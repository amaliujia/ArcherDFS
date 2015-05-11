package MapReduce.Application;

import MapReduce.JobTracker.SDJobConfig;
import MapReduce.JobTracker.SDJobTracker;
import Protocol.Client.SDMapReduceClientService;
import Util.SDUtil;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
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

    public void run(SDJobConfig jobConfig, Class<?> mapReduceClass) throws RemoteException, NotBoundException {
        this.jobConfig = jobConfig;
        Registry registry = LocateRegistry.getRegistry(mapreduceServiceHost, mapreducePort);
        sdMapReduceClientService = (SDMapReduceClientService) registry.lookup(SDJobTracker.class.getCanonicalName() + "client");
        sdMapReduceClientService.submitJob(this.jobConfig);
    }

}
