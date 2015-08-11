package MapReduce.JobTracker;

import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDReducerTask;
import MapReduce.TaskTracker.SDRemoteTaskObject;
import Protocol.MapReduce.SDJobService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author amaliujia
 */
public class SDJobTrackerRMIService extends UnicastRemoteObject implements SDJobService {

    private SDJobTracker jobTracker;

    public SDJobTrackerRMIService(SDJobTracker aJobTracker) throws RemoteException {
        super();
        this.jobTracker = aJobTracker;
    }

    /**
     * heartbeat in Map Reduce layer. In order for map reduce to moniter status of
     * slave node, aka task trackers.
     *
     * @param taskObject
     *          Descriptor of task trakcer.
     * @throws RemoteException
     *          throws when network fail.
     */
    public void heartbeat(SDRemoteTaskObject taskObject) throws RemoteException {
        jobTracker.updateTaskTracker(taskObject);
    }

    public void mapperTaskSucceed(SDMapperTask task) throws RemoteException {
        jobTracker.mapperTaskSucceed(task);
    }

    public void reducerTaskSucceed(SDReducerTask task) throws RemoteException {

    }

    public void mapperTaskFailed(SDMapperTask task) throws RemoteException {
        jobTracker.mapperTaskFailed(task);
    }

    public void reducerTaskFailed(SDReducerTask task) throws RemoteException {
        jobTracker.reducerTaskFailed(task);
    }

    public void reducerTaskFailedOnMapperTask(SDReducerTask reducerTask,
                                              SDMapperTask mapperTask)
                                                throws RemoteException
    {

    }
}
