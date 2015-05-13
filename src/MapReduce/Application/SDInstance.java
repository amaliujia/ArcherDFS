package MapReduce.Application;

import MapReduce.JobTracker.SDJobConfig;
import Protocol.Client.SDMapReduceClientService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * One instance of job to execute a user define map reduce.
 *
 * @author amaliujia
 */
public class SDInstance {
    private SDJobConfig jobConfig;
    private String mapreduceServiceHost;
    private int mapreducePort;
    private SDMapReduceClientService sdMapReduceClientService;

    public SDInstance(String host, int port){
        mapreducePort = port;
        mapreduceServiceHost = host;
    }

    /**
     * A class to submit user-defined Map Reduce and related parameter..
     * @param jobConfig
     * @throws RemoteException
     * @throws NotBoundException
     */
    public void run(SDJobConfig jobConfig) throws RemoteException, NotBoundException {
        this.jobConfig = jobConfig;
        Registry registry = LocateRegistry.getRegistry(mapreduceServiceHost, mapreducePort);
        sdMapReduceClientService = (SDMapReduceClientService) registry.lookup(SDMapReduceClientService.class.getCanonicalName());
        sdMapReduceClientService.submitJob(this.jobConfig);
    }

}
