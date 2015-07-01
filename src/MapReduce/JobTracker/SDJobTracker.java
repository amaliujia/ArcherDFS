package MapReduce.JobTracker;

import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDReducerTask;
import MapReduce.DispatchUnits.SDTaskStatus;
import MapReduce.TaskTracker.SDRemoteTaskObject;
import MapReduce.Util.SDMapReduceConstant;
import Protocol.Client.SDMapReduceClientService;
import Protocol.MapReduce.SDJobService;
import Protocol.MapReduce.SDTaskService;
import Util.SDUtil;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author amaliujia
 */
public class SDJobTracker {
    private static Logger log4jLogger = Logger.getLogger(SDJobTracker.class);

    private ConcurrentHashMap<String, SDRemoteTaskObject> taskTackers;
    private ConcurrentHashMap<Integer, SDJobUnit> jobs;
    private PriorityBlockingQueue<SDMapperTask> mapperTasks;
    private SDJobService sdJobTrackerRMIService;
    private SDMapReduceClientService sdMapReduceClientService;
    private Registry registry;
    private Thread schedulerThread;


    //thread pool to execute jobs
    ThreadPoolExecutor jobExecutor;

    public void startService(){
        try {
            bindRMIService();
            bindClientRMIService();
        } catch (RemoteException e) {
            log4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + "cannot bind RMI service " +
                                SDJobTrackerRMIService.class.getCanonicalName() +
                                " on port " + SDMapReduceConstant.JobTrackerServicePort);
            e.printStackTrace();
        }
        initThreads();
    }

    /**
     * Submit a job into thread pool.
     * @param jobConfig
     *          Description of a job.
     */
    public void submitJob(SDJobConfig jobConfig){
        SDJobUnit unit = new SDJobUnit(jobConfig);
        jobs.put(unit.getID(), unit);
        Runnable runnable = new SDJobInitializationUnit(unit, this);
        jobExecutor.execute(runnable);
        log4jLogger.info(SDUtil.LOG4JINFO_MAPREDUCE + "job " + jobConfig.jobName + " sumbit");
    }

    /**
     * Init thread pool for handling
     */
    private void initThreads(){
        //threadsTasksQueue = new LinkedBlockingDeque<Runnable>();
        jobExecutor = new ThreadPoolExecutor(4, 6, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
        jobs = new ConcurrentHashMap<Integer, SDJobUnit>();
        //mapperTasks = new ConcurrentLinkedQueue<SDMapperTask>();
        mapperTasks = new PriorityBlockingQueue<SDMapperTask>();

        //start scheduler
        schedulerThread = new Thread(new SDTaskScheduler(this));
        schedulerThread.start();
    }


    /**
     * Bind job RMI service on master node.
     * @throws RemoteException
     */
    private void bindRMIService() throws RemoteException {
        sdJobTrackerRMIService = new SDJobTrackerRMIService(this);
        registry = LocateRegistry.getRegistry(SDUtil.MASTER_RMIRegistry_PORT);
        registry.rebind(SDJobService.class.getCanonicalName(), sdJobTrackerRMIService);
        log4jLogger.debug(SDUtil.LOG4JDEBUG_MAPREDUCE + "bing service " +
                SDJobTrackerRMIService.class.getCanonicalName() +
                " on port " + SDUtil.MASTER_RMIRegistry_PORT);
    }

    /**
     * Bind map reduce client RMI service on master node.
     * @throws RemoteException
     */
    private void bindClientRMIService() throws RemoteException {
        sdMapReduceClientService = new SDMapReduceClientRMIService(this);
        registry = LocateRegistry.getRegistry(SDUtil.MASTER_RMIRegistry_PORT);
        registry.rebind(SDMapReduceClientService.class.getCanonicalName(), sdMapReduceClientService);
        log4jLogger.debug(SDUtil.LOG4JDEBUG_MAPREDUCE + "bing service " +
                SDJobTracker.class.getCanonicalName() + "client" +
                " on port " + SDUtil.MASTER_RMIRegistry_PORT);
    }

    public SDRemoteTaskObject getMapperTaskTracker(){
        String s  = taskTackers.keySet().iterator().next();
        SDRemoteTaskObject result = null;
        if(s != null){
            result = taskTackers.get(s);
        }else {
            return result;
        }

        Iterator<SDRemoteTaskObject> iterator = taskTackers.values().iterator();
        while (iterator.hasNext()){
            SDRemoteTaskObject o = iterator.next();
            if(o.isValid() && (o.getMapperTaskNumber() < result.getMapperTaskNumber())){
                result = o;
            }
        }
        return result;
    }

    public SDRemoteTaskObject getReducerTaskTracker(){
        String s  = taskTackers.keySet().iterator().next();
        SDRemoteTaskObject result = null;
        if(s != null){
            result = taskTackers.get(s);
        }else {
            return result;
        }
        Iterator<SDRemoteTaskObject> iterator = taskTackers.values().iterator();
        while (iterator.hasNext()){
            SDRemoteTaskObject o = iterator.next();
            if (o.isValid() && (o.getReduceTaskNumber() < result.getReduceTaskNumber())){
                result = o;
            }
        }
        return result;
    }

    public SDMapperTask getMapperTaskInQueue() throws InterruptedException{
        return mapperTasks.take();
    }

    /**
     * Shutdown thread pool when necessary.
     */
    protected void finalize(){
        jobExecutor.shutdown();

        try {
            schedulerThread.join();
        } catch (InterruptedException e) {
            log4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + "scheduler thread join interrupted");
        }
    }

    public void addMapperTask(SDMapperTask task){
        mapperTasks.offer(task);
    }

    public void updateTaskTracker(SDRemoteTaskObject object){
        object.setTimestamp();
        taskTackers.put(object.getHostname(), object);
    }

    public void mapperTaskSucceed(SDMapperTask task){
        SDJobUnit curJob = jobs.get(task.getJobID());
        log4jLogger.info(SDUtil.LOG4JINFO_MAPREDUCE + "Mapper Task finished");
        SDMapperTask t = curJob.getMapperTask(task.getTaskID());
        t.setTaskStatus(SDTaskStatus.SUCCESS);

        //schedule reducer task.
        /****************************************************************************************
         * For now, the basic MapReducer Job schedule policy is, when a mapper task finishes,
         * a bunch of reducer tasks will be launched, based on specified reducer amount, and each
         * reducer task is in charge of reading a output sharding of a mapper task, handling this
         * sharding ,and writing final result to distributed file system.
         *
         * Cons: Simplicity. This idea is really easy to implement.
         *
         * Pros: It would create many java threads, may result in inefficiency, and slow the whole
         * system.
         ****************************************************************************************/
        runReducerTasksForMapperTask(curJob, task);
    }

    private void runReducerTasksForMapperTask(SDJobUnit unit, SDMapperTask task){
        List<SDReducerTask> reducerTasks = unit.getReducerTasksInArray();
        for (SDReducerTask t : reducerTasks) {
          launchSingleReducer(t, task);
        }
    }

    private void launchSingleReducer(SDReducerTask reducerTask, SDMapperTask mapperTask){
        SDRemoteTaskObject taskObject = reducerTask.getTaskTracker();
        try {
            Registry registry = LocateRegistry.getRegistry(taskObject.getHostname(), taskObject.getHostport());
            //if service name is ok?
            SDTaskService service = (SDTaskService)registry.lookup(SDTaskService.class.getCanonicalName());
            service.runReducerTask(mapperTask, reducerTask);
        } catch (Exception e) {
            reducerTaskFailed(reducerTask);
        }
    }

    public void mapperTaskFailed(SDMapperTask task){

    }

    public void reducerTaskFailed(SDReducerTask task){

    }
}
