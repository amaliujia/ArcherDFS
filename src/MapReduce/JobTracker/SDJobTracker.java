package MapReduce.JobTracker;

import MapReduce.TaskTracker.SDTaskObject;
import MapReduce.Util.SDMapReduceConstant;
import Protocol.Client.SDMapReduceClientService;
import Protocol.MapReduce.SDJobService;
import Util.SDUtil;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author amaliujia
 */
public class SDJobTracker {
    private static Logger log4jLogger = Logger.getLogger(SDJobTracker.class);

    private ConcurrentHashMap<String, SDTaskObject> taskTackers;
    private SDJobService sdJobTrackerRMIService;
    private SDMapReduceClientService sdMapReduceClientService;
    private Registry registry;

    public void startService(){
        try {
            bindRMIService();
            bindClientRMIService();
        } catch (RemoteException e) {
            log4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + "cannot bind RMI service " +
                                SDJobTrackerRMIService.class.getCanonicalName() +
                                " on port " + SDMapReduceConstant.JobTrackerServicePort);
            e.printStackTrace();
        }
    }

    public void submitJob(SDJobConfig jobConfig){

    }

    private void bindRMIService() throws RemoteException {
        sdJobTrackerRMIService = new SDJobTrackerRMIService(this);
        registry = LocateRegistry.getRegistry(SDUtil.MASTER_RMIRegistry_PORT);
        registry.rebind(SDJobTracker.class.getCanonicalName(), sdJobTrackerRMIService);
        log4jLogger.debug(SDUtil.LOG4JDEBUG_MAPREDUCE + "bing service "+
                            SDJobTrackerRMIService.class.getCanonicalName() +
                         " on port " + SDUtil.MASTER_RMIRegistry_PORT);
    }

    private void bindClientRMIService() throws RemoteException {
        sdMapReduceClientService = new SDMapReduceClientRMIService(this);
        registry = LocateRegistry.getRegistry(SDUtil.MASTER_RMIRegistry_PORT);
        registry.rebind(SDJobTracker.class.getCanonicalName() + "client", sdMapReduceClientService);
        log4jLogger.debug(SDUtil.LOG4JDEBUG_MAPREDUCE + "bing service " +
                SDJobTracker.class.getCanonicalName() + "client" +
                " on port " + SDUtil.MASTER_RMIRegistry_PORT);
    }
}
