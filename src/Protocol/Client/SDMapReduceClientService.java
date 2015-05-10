package Protocol.Client;

import MapReduce.JobTracker.SDJobConfig;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by amaliujia on 15-5-9.
 */
public interface SDMapReduceClientService extends Remote {
    public void submitJob(SDJobConfig jobConfig) throws RemoteException;

}
