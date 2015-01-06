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
    protected SDJobRMIService() throws RemoteException {
    }

    @Override
    public void heartbeat(SDTaskTrackerInfo taskTrackerInfo) throws RemoteException {

    }

    @Override
    public void mapperTaskSucceed(SDMapperTask task) throws RemoteException {

    }

    @Override
    public void reducerTaskSucceed(SDReducerTask task) throws RemoteException {

    }

    @Override
    public void mapperTaskFailed(SDMapperTask task) throws RemoteException {

    }

    @Override
    public void reducerTaskFailed(SDReducerTask task) throws RemoteException {

    }

    @Override
    public void reducerTaskFailedOnMapperTask(SDReducerTask reducerTask, SDMapperTask mapperTask) throws RemoteException {

    }
}