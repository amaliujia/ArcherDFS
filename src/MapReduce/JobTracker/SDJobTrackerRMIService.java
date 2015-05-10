package MapReduce.JobTracker;

import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDReducerTask;
import MapReduce.TaskTracker.SDTaskObject;
import Protocol.MapReduce.SDJobService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by amaliujia on 15-5-9.
 */
public class SDJobTrackerRMIService extends UnicastRemoteObject implements SDJobService {

    private SDJobTracker jobTracker;

    public SDJobTrackerRMIService(SDJobTracker aJobTracker) throws RemoteException {
        super();
        this.jobTracker = aJobTracker;
    }

    public void heartbeat(SDTaskObject taskObject) throws RemoteException {

    }

    public void mapperTaskSucceed(SDMapperTask task) throws RemoteException {

    }

    public void reducerTaskSucceed(SDReducerTask task) throws RemoteException {

    }

    public void mapperTaskFailed(SDMapperTask task) throws RemoteException {

    }

    public void reducerTaskFailed(SDReducerTask task) throws RemoteException {

    }

    public void reducerTaskFailedOnMapperTask(SDReducerTask reducerTask,
                                              SDMapperTask mapperTask)
                                                throws RemoteException
    {

    }
}
