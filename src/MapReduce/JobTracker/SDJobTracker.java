package MapReduce.JobTracker;

import MapReduce.TaskTracker.SDTaskObject;
import MapReduce.Util.SDMapReduceConstant;

import Util.SDUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by amaliujia on 15-5-9.
 */
public class SDJobTracker {
    private static Logger log4jLogger = Logger.getLogger(SDJobTracker.class);

    private ConcurrentHashMap<String, SDTaskObject> taskTackers;
    private SDJobTrackerRMIService sdJobTrackerRMIService;
    private Registry registry;

    public void startService(){
        try {
            bindRMIService();
        } catch (RemoteException e) {
            log4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + "cannot bind RMI service " +
                                SDJobTrackerRMIService.class.getCanonicalName() +
                                " on port " + SDMapReduceConstant.JobTrackerServicePort);
            e.printStackTrace();
        }


    }

    private void bindRMIService() throws RemoteException {
        sdJobTrackerRMIService = new SDJobTrackerRMIService(this);
        registry = LocateRegistry.createRegistry(SDMapReduceConstant.JobTrackerServicePort);
        registry.rebind(SDJobTracker.class.getCanonicalName(), sdJobTrackerRMIService);
        log4jLogger.debug(SDUtil.LOG4JDEBUG_MAPREDUCE + "bing service "+
                            SDJobTrackerRMIService.class.getCanonicalName() +
                         " on port " + SDMapReduceConstant.JobTrackerServicePort);
    }

}
