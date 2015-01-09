package MapReduce.Master;

import MapReduce.Slave.SDTaskTrackerInfo;
import MapReduce.Task.SDMapperTask;
import MapReduce.Task.SDReducerTask;
import Protocol.MapReduce.SDJobService;
import Util.SDUtil;

import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by amaliujia on 15-1-4.
 */
public class SDJobTracker {

    private ConcurrentHashMap<String, SDTaskTrackerInfo> taskTrackerInfos;

    private ConcurrentHashMap<Integer, SDJobInfo> jobs;

    private PriorityQueue<SDMapperTask> mapperTasks;

    private ExecutorService threadpool;

    private ScheduledExecutorService scheduledExecutorService;

    private Thread scheduler;

    private Registry registry;

    private SDJobService service;

    public SDJobTracker(){
        taskTrackerInfos = new ConcurrentHashMap<String, SDTaskTrackerInfo>();
        jobs = new ConcurrentHashMap<Integer, SDJobInfo>();
        mapperTasks = new PriorityQueue<SDMapperTask>();
        scheduledExecutorService = Executors.newScheduledThreadPool(10);
        threadpool = Executors.newFixedThreadPool(10);
    }

    void start() throws Exception{
        bindService();
        //TODO: checker, scheduler and fileserver.


    }

    private void bindService() throws RemoteException, UnknownHostException {
        service = new SDJobRMIService(this);
        registry = LocateRegistry.getRegistry(SDUtil.getlocalHost(), SDUtil.MASTER_RMIRegistry_PORT);
        registry.rebind(SDJobService.class.getCanonicalName(), service);
        //TODO: rebind client rmi service
    }

    
    public void heartbeat(SDTaskTrackerInfo taskTrackerInfo) throws RemoteException {

    }

    private void updateTaskTracker(SDTaskTrackerInfo taskTrackerInfo){
          SDTaskTrackerInfo info= taskTrackerInfos.putIfAbsent(taskTrackerInfo.toString(), taskTrackerInfo);
          if (info != null){
          }else{

          }
//        TaskTrackerInfo old = taskTackers.putIfAbsent(taskTracker.toString(), taskTracker);
//        if(old != null){
//            old.setTimestamp(System.currentTimeMillis());
//            old.setMapperTaskNumber(taskTracker.getMapperTaskNumber());
//            old.setReduceTaskNumber(taskTracker.getReducerTaskNumber());
//        } else {
//            taskTracker.setTimestamp(System.currentTimeMillis());
//        }
    }


    public void mapperTaskSucceed(SDMapperTask task) throws RemoteException {

    }

    
    public void reducerTaskSucceed(SDReducerTask task) throws RemoteException {

    }

    
    public void mapperTaskFailed(SDMapperTask task) throws RemoteException {

    }

    
    public void reducerTaskFailed(SDReducerTask task) throws RemoteException {

    }

    
    public void reducerTaskFailedOnMapperTask(SDReducerTask reducerTask, SDMapperTask mapperTask) throws RemoteException {

    }
}
