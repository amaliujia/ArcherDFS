package MapReduce.Master;

import MapReduce.Slave.SDTaskTrackerInfo;
import MapReduce.Task.SDMapperTask;
import MapReduce.Task.SDReducerTask;
import Protocol.MapReduce.SDJobService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by amaliujia on 15-1-5.
 */
public class SDJobRMIService extends UnicastRemoteObject implements SDJobService {

    private SDJobTracker jobTracker;

    public SDJobRMIService(SDJobTracker tracker) throws RemoteException {
        super();
        jobTracker = tracker;
    }

    @Override
    public void heartbeat(SDTaskTrackerInfo taskTrackerInfo) throws RemoteException {
        jobTracker.heartbeat(taskTrackerInfo);
    }

    @Override
    public void mapperTaskSucceed(SDMapperTask task) throws RemoteException {
        jobTracker.mapperTaskSucceed(task);
    }

    @Override
    public void reducerTaskSucceed(SDReducerTask task) throws RemoteException {
        jobTracker.reducerTaskSucceed(task);
    }

    @Override
    public void mapperTaskFailed(SDMapperTask task) throws RemoteException {
        jobTracker.mapperTaskFailed(task);
    }

    @Override
    public void reducerTaskFailed(SDReducerTask task) throws RemoteException {
        jobTracker.reducerTaskFailed(task);
    }

    @Override
    public void reducerTaskFailedOnMapperTask(SDReducerTask reducerTask, SDMapperTask mapperTask)
                                            throws RemoteException {
        jobTracker.reducerTaskFailedOnMapperTask(reducerTask, mapperTask);
    }
}
