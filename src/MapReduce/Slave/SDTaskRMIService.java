package MapReduce.Slave;

import MapReduce.Task.SDMapperTask;
import MapReduce.Task.SDReducerTask;
import Protocol.MapReduce.SDTaskService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Created by amaliujia on 15-1-5.
 */
public class SDTaskRMIService extends UnicastRemoteObject implements SDTaskService {

    private SDTaskTracker taskTracker;

    public SDTaskRMIService(SDTaskTracker taskTracker) throws RemoteException {
        super();
        this.taskTracker = taskTracker;
    }

    @Override
    public void runMapperTask(SDMapperTask task) throws RemoteException {
        taskTracker.runMapperTask(task);
    }

    @Override
    public void runReducerTask(SDMapperTask mapperTask, List<SDReducerTask> reducerTasks) throws RemoteException {
        taskTracker.runReducerTask(mapperTask, reducerTasks);
    }
}
