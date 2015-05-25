package MapReduce.TaskTracker;

import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.JobTracker.SDJobTracker;
import Protocol.MapReduce.SDJobService;
import Protocol.MapReduce.SDTaskService;
import Util.SDUtil;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

    public void startService() throws RemoteException, NotBoundException {

        numMapperTasks = 0;
        numReducerTasks = 0;

        registry = LocateRegistry.createRegistry(SDUtil.SALVE_RMIREGISTRY_PORT);
        taskService = new SDTaskTrackerRMIService(this);
        registry.rebind(SDTaskTracker.class.getCanonicalName(), taskService);
        registry = LocateRegistry.getRegistry(SDUtil.masterAddress, SDUtil.MASTER_RMIRegistry_PORT);
        jobService = (SDJobService) registry.lookup(SDJobTracker.class.getCanonicalName());

        heartbeatService = Executors.newScheduledThreadPool(10);
        heartbeatService.scheduleAtFixedRate(new SDTaskHeartbeatJob(registry, this),
                0, 1000, TimeUnit.SECONDS);
    }

    public void runMapperTask(SDMapperTask task){

    }

    public int getNumMapperTasks(){
        return numMapperTasks;
    }

    public int getNumReducerTasks(){
        return numReducerTasks;
    }
}
