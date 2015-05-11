package MapReduce.JobTracker;

import Protocol.Client.SDMapReduceClientService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author amaliujia
 */
public class SDMapReduceClientRMIService extends UnicastRemoteObject implements SDMapReduceClientService {
    private SDJobTracker jobTracker;

    public SDMapReduceClientRMIService(SDJobTracker aJobTracker) throws RemoteException {
        super();
        this.jobTracker = aJobTracker;
    }

    public void submitJob(SDJobConfig jobConfig) throws RemoteException {
        jobTracker.submitJob(jobConfig);
    }
}
