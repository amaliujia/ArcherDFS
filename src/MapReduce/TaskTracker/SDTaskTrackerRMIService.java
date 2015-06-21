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

    SDTaskTracker taskTracker;

    public SDTaskTrackerRMIService(SDTaskTracker taskTracker) throws RemoteException {
        super();
        this.taskTracker = taskTracker;
    }

    public void runMapperTask(SDMapperTask task) throws RemoteException {
        taskTracker.runMapperTask(task);
    }

    public void runReducerTask(SDMapperTask mapperTask,
                               SDReducerTask reducerTasks)
                                throws RemoteException
    {
        taskTracker.runReducerTask(reducerTasks, mapperTask);
    }
}
