package MapReduce.JobTracker;

import Protocol.Client.SDMapReduceClientService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by amaliujia on 15-5-9.
 */
public class SDMapReduceClientRMIService extends UnicastRemoteObject implements SDMapReduceClientService {
    private SDJobTracker jobTracker;

    public SDMapReduceClientRMIService(SDJobTracker aJobTracker) throws RemoteException {
        super();
        this.jobTracker = aJobTracker;
    }

    public void submitJob(SDJobConfig jobConfig) throws RemoteException {

    }
}
