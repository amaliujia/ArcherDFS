package MapReduce.Slave;

import MapReduce.Common.SDMapReduce;
import MapReduce.Task.SDMapperTask;
import MapReduce.Task.SDReducerTask;
import MapReduce.Task.SDTask;
import MapReduce.Util.SDMapReduceConstants;
import Protocol.MapReduce.SDJobService;
import Protocol.MapReduce.SDTaskService;
import Util.SDUtil;

import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

/**
 * Created by amaliujia on 15-1-4.
 */
public class SDTaskTracker {

    private SDTaskService taskService;

    private int registryPort;

    private SDTaskTrackerInfo taskTrackerInfo;

    private String jobTrackerRegistryHost;

    private int jobTrackerRegistryPort;

    private Registry jobTrackerRegistry;

    private SDJobService jobTrackerService;

    public void runMapperTask(SDMapperTask task) throws RemoteException {
        task.setOutputDir(SDMapReduceConstants.DEFAULT_OUTPUT_DIR);
//        task.setFileServerHost(taskTrackerInfo.getHost());
//        task.setFileServerPort(taskTrackerInfo.getFileServerPort());
//        task.createTaskFolder();
//        threadPool.execute(new TaskTrackerMapperWorker(task, this));
//        taskTrackerInfo.increaseMapperTaskNumber();
    }

    public void runReducerTask(SDMapperTask mapperTask, List<SDReducerTask> reducerTasks) throws RemoteException {

    }

    public void start(){
        bindService();
    }

    private void bindService() {
        try{
            taskService= new SDTaskRMIService(this);
            Registry registry = LocateRegistry.getRegistry(SDUtil.getlocalHost(), registryPort);
            registry.rebind(taskTrackerInfo.toString(), taskService);
            jobTrackerRegistry = LocateRegistry.getRegistry(jobTrackerRegistryHost, jobTrackerRegistryPort);
            jobTrackerService = (SDJobService)jobTrackerRegistry.lookup(SDJobService.class.getCanonicalName());
        } catch (RemoteException e){
            SDUtil.fatalError("registry server error");
        } catch (NotBoundException e) {
            SDUtil.fatalError("jobtracker not bind");
        } catch (UnknownHostException e) {
            SDUtil.fatalError("can't resolve host name");
        }
    }

    public void heartbeat(){
        try {
            jobTrackerService.heartbeat(taskTrackerInfo);
        } catch (RemoteException e) {
            System.err.println("can't heartbeat with job tracker" + "  " + e.toString());
        }
    }


    public String getJobTrackerRegistryHost() {
        return jobTrackerRegistryHost;
    }

    public int getJobTrackerRegistryPort() {
        return jobTrackerRegistryPort;
    }

    public int getRegistryPort() {
        return registryPort;
    }

    public Registry getJobTrackerRegistry(){
        return jobTrackerRegistry;
    }


}
