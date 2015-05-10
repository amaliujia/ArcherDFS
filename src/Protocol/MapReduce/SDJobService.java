package Protocol.MapReduce;

import MapReduce.Abstraction.SDMapperTask;
import MapReduce.Abstraction.SDReducerTask;
import MapReduce.TaskTracker.SDTaskObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by amaliujia on 15-1-5.
 */
public interface SDJobService extends Remote{

    public void heartbeat(SDTaskObject taskObject) throws RemoteException;

     void mapperTaskSucceed(SDMapperTask task) throws RemoteException;


     void reducerTaskSucceed(SDReducerTask task) throws RemoteException;

     void mapperTaskFailed(SDMapperTask task) throws RemoteException;

     void reducerTaskFailed(SDReducerTask task) throws RemoteException;

     void reducerTaskFailedOnMapperTask(SDReducerTask reducerTask, SDMapperTask mapperTask) throws RemoteException;
}
