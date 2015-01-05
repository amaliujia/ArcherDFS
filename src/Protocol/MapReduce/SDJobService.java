package Protocol.MapReduce;

import MapReduce.Slave.SDTaskTrackerInfo;
import MapReduce.Task.SDMapperTask;
import MapReduce.Task.SDReducerTask;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by amaliujia on 15-1-5.
 */
public interface SDJobService extends Remote{

    public void heartbeat(SDTaskTrackerInfo taskTrackerInfo) throws RemoteException;
    public void mapperTaskSucceed(SDMapperTask task) throws RemoteException;
    public void reducerTaskSucceed(SDReducerTask task) throws RemoteException;
    public void mapperTaskFailed(SDMapperTask task) throws RemoteException;
    public void reducerTaskFailed(SDReducerTask task) throws RemoteException;
    public void reducerTaskFailedOnMapperTask(SDReducerTask reducerTask, SDMapperTask mapperTask) throws RemoteException;
}
