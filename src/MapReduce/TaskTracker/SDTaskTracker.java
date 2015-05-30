package MapReduce.TaskTracker;

import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.JobTracker.SDJobTracker;
import MapReduce.Util.SDMapReduceConstant;
import Protocol.MapReduce.SDJobService;
import Protocol.MapReduce.SDTaskService;
import Util.SDUtil;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    }

    public void runMapperTask(SDMapperTask task){
        task.setOutputDir(SDMapReduceConstant.MAP_OUTPUT_DIR);

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
        numMapperTasks--;
    }

    public void mapreduceTaskFail(SDMapperTask task){
        try {
            jobService.mapperTaskFailed(task);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        numMapperTasks--;
    }
}
