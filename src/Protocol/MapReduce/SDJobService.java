package Protocol.MapReduce;

import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDReducerTask;
import MapReduce.TaskTracker.SDRemoteTaskObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by amaliujia on 15-1-5.
 */
public interface SDJobService extends Remote{

    public void heartbeat(SDRemoteTaskObject taskObject) throws RemoteException;

     void mapperTaskSucceed(SDMapperTask task) throws RemoteException;

     void reducerTaskSucceed(SDReducerTask task) throws RemoteException;

     void mapperTaskFailed(SDMapperTask task) throws RemoteException;

     void reducerTaskFailed(SDReducerTask task) throws RemoteException;

     void reducerTaskFailedOnMapperTask(SDReducerTask reducerTask, SDMapperTask mapperTask) throws RemoteException;
}
