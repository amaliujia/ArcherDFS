package Protocol.MapReduce;

import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDReducerTask;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 * Created by amaliujia on 15-1-5.
 */
public interface SDTaskService extends Remote {

    public void runMapperTask(SDMapperTask task) throws RemoteException;

    public void runReducerTask(SDMapperTask mapperTask, List<SDReducerTask> reducerTasks) throws RemoteException;
}
