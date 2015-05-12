package MapReduce.JobTracker;

import MapReduce.TaskTracker.SDTaskObject;
import MapReduce.Util.SDMapReduceConstant;
import Protocol.Client.SDMapReduceClientService;
import Protocol.MapReduce.SDJobService;
import Util.SDUtil;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author amaliujia
 */
public class SDJobTracker {
    private static Logger log4jLogger = Logger.getLogger(SDJobTracker.class);

    private ConcurrentHashMap<String, SDTaskObject> taskTackers;
    private SDJobService sdJobTrackerRMIService;
    private SDMapReduceClientService sdMapReduceClientService;
    private Registry registry;
    private ConcurrentHashMap<Integer, SDJobUnit> jobs;

    //thread pool to execute jobs
    ThreadPoolExecutor jobExecutor;

    //Blocking queue for thread pool.
    //LinkedBlockingDeque<Runnable> threadsTasksQueue;

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

    private void initThreads(){
        //threadsTasksQueue = new LinkedBlockingDeque<Runnable>();
        jobExecutor = new ThreadPoolExecutor(4, 6, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
        jobs = new ConcurrentHashMap<Integer, SDJobUnit>();
    }


    private void bindRMIService() throws RemoteException {
        sdJobTrackerRMIService = new SDJobTrackerRMIService(this);
        registry = LocateRegistry.getRegistry(SDUtil.MASTER_RMIRegistry_PORT);
        registry.rebind(SDJobTracker.class.getCanonicalName(), sdJobTrackerRMIService);
        log4jLogger.debug(SDUtil.LOG4JDEBUG_MAPREDUCE + "bing service "+
                            SDJobTrackerRMIService.class.getCanonicalName() +
                         " on port " + SDUtil.MASTER_RMIRegistry_PORT);
    }

    private void bindClientRMIService() throws RemoteException {
        sdMapReduceClientService = new SDMapReduceClientRMIService(this);
        registry = LocateRegistry.getRegistry(SDUtil.MASTER_RMIRegistry_PORT);
        registry.rebind(SDJobTracker.class.getCanonicalName() + "client", sdMapReduceClientService);
        log4jLogger.debug(SDUtil.LOG4JDEBUG_MAPREDUCE + "bing service " +
                SDJobTracker.class.getCanonicalName() + "client" +
                " on port " + SDUtil.MASTER_RMIRegistry_PORT);
    }

    /**
     * Shutdown thread pool when necessary.
     */
    protected void finalize(){
        jobExecutor.shutdown();
    }
}
