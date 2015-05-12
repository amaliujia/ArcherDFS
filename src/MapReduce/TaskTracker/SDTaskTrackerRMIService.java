package MapReduce.TaskTracker;

import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDReducerTask;
import Protocol.MapReduce.SDTaskService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * @author amaliujia
 */
public class SDTaskTrackerRMIService extends UnicastRemoteObject implements SDTaskService {

    public SDTaskTrackerRMIService() throws RemoteException {
        super();
    }

    public void runMapperTask(SDMapperTask task) throws RemoteException {

    }

    public void runReducerTask(SDMapperTask mapperTask,
                               List<SDReducerTask> reducerTasks)
                                throws RemoteException
    {

    }
}
