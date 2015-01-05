package Protocol.MapReduce;

import MapReduce.Task.SDMapperTask;
import MapReduce.Task.SDReducerTask;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by amaliujia on 15-1-5.
 */
public interface SDTaskService {
    public void runMapperTask(SDMapperTask task) throws RemoteException;
    public void runReducerTask(SDMapperTask mapperTask, List<SDReducerTask> reducerTasks) throws RemoteException;
}
