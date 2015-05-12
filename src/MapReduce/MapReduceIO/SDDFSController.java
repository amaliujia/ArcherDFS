package MapReduce.MapReduceIO;

import Protocol.DFS.MasterService.SDMasterService;
import Util.SDUtil;
import org.apache.log4j.Logger;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by amaliujia on 15-5-12.
 */
public class SDDFSController {

    public static Logger LOG4jLogger = Logger.getLogger(SDDFSController.class);

    private static SDDFSController client = null;

    private String host;
    private int port;
    private SDMasterService masterService;

    public static SDDFSController instance(){
        if(client == null){
            LOG4jLogger.info(SDUtil.LOG4JINFO_MAPREDUCE + "initialize DFS controller");
            client.host = SDUtil.masterAddress;
            client.port = SDUtil.MASTER_RMIRegistry_PORT;
            client.connect();
        }
        return client;
    }

    private void connect(){
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            masterService = (SDMasterService)registry.lookup(SDMasterService.class.getCanonicalName());
        } catch (RemoteException e) {
            LOG4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + "DFS Controller cannot get stub of DFS master RMI service");
            e.printStackTrace();
        } catch (NotBoundException e) {
            LOG4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + SDMasterService.class.getCanonicalName() + " doesn't bount");
            e.printStackTrace();
        }
    }

}
