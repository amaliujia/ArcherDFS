package MapReduce.TaskTracker;

import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDReducerTask;
import MapReduce.JobTracker.SDJobTracker;
import MapReduce.Util.SDMapReduceConstant;
import Protocol.MapReduce.SDJobService;
import Protocol.MapReduce.SDTaskService;
import Util.SDUtil;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.*;

/**
 * @author amaliujia
 */
public class SDTaskTracker {
    private ScheduledExecutorService heartbeatService;
    private SDJobService jobService;
    private SDTaskService taskService;
    private Registry registry;
    private int numMapperTasks;
    private int numReducerTasks;
    private ExecutorService threadPool;
    private ConcurrentHashMap<Integer, SDTaskExecuteReducerWorker> reducerTaskWorkers;
    private String lock = "counterlock";

    public void startService() throws RemoteException, NotBoundException {

        numMapperTasks = 0;
        numReducerTasks = 0;

        registry = LocateRegistry.createRegistry(SDUtil.SALVE_RMIREGISTRY_PORT);
        taskService = new SDTaskTrackerRMIService(this);
        registry.rebind(SDTaskTracker.class.getCanonicalName(), taskService);
        registry = LocateRegistry.getRegistry(SDUtil.masterAddress, SDUtil.MASTER_RMIRegistry_PORT);
        jobService = (SDJobService) registry.lookup(SDJobTracker.class.getCanonicalName());

        heartbeatService = Executors.newScheduledThreadPool(1);
        heartbeatService.scheduleAtFixedRate(new SDTaskHeartbeatJob(registry, this),
                0, 1000, TimeUnit.SECONDS);

        // TODO:: number of threads in pool needs tuning.
        threadPool = Executors.newScheduledThreadPool(5);

        reducerTaskWorkers = new ConcurrentHashMap<Integer, SDTaskExecuteReducerWorker>();
    }

    public void runMapperTask(SDMapperTask task){
        task.setOutputDir(SDMapReduceConstant.MAP_OUTPUT_DIR);
        increaseNumOfMapper();
        //ready to run
        threadPool.execute(new SDTaskExecuteMapperWorker(this, task));
    }

    public int getNumMapperTasks(){
        return numMapperTasks;
    }

    public int getNumReducerTasks(){
        return numReducerTasks;
    }

    public void mapreduceTaskSucceed(SDMapperTask task){
        try {
            jobService.mapperTaskSucceed(task);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        decreaseNumOfMapper();
    }

    public void mapreduceTaskFail(SDMapperTask task){
        try {
            jobService.mapperTaskFailed(task);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        decreaseNumOfMapper();
    }

    private void decreaseNumOfMapper(){
        synchronized (lock){
            numMapperTasks--;
        }
    }

    private void increaseNumOfMapper(){
        synchronized (lock){
            numMapperTasks++;
        }
    }

    private void decreaseNumofReducer(){
        synchronized (lock){
            numReducerTasks--;
        }
    }

    private void increaseNumOfReducer(){
        synchronized (lock){
            numReducerTasks++;
        }
    }

    public void runReducerTask(SDReducerTask reducerTask, SDMapperTask mapperTask){
        increaseNumOfReducer();
        // how to make it work here?
        SDTaskExecuteReducerWorker worker = null;
        if(reducerTaskWorkers.containsKey(reducerTask.getTaskID())){
            worker = reducerTaskWorkers.get(reducerTask.getTaskID());
            worker.addMapperTask(mapperTask);
        }else{
            worker = new SDTaskExecuteReducerWorker(reducerTask, this);
            worker.addMapperTask(mapperTask);
            reducerTaskWorkers.put(reducerTask.getTaskID(), worker);
            threadPool.execute(worker);
        }
    }
}
