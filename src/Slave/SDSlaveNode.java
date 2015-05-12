package Slave;

import FileSystem.Slave.SDSlaveHeartbreatsJob;
import FileSystem.Slave.SDSlaveIO;
import FileSystem.Slave.SDSlaveRMIService;
import Protocol.DFS.MasterService.SDMasterService;
import Protocol.DFS.SlaveService.SDSlaveService;
import Util.SDUtil;
import org.apache.log4j.Logger;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author amaliujia
 */
public class SDSlaveNode {
    private static Logger Log4jLogger = Logger.getLogger(SDSlaveNode.class);

    public SDSlaveIO slaveIO;

    private Registry registry;

    private String serviceName;

    private SDMasterService masterService;

    private ScheduledExecutorService heartbeatService;

    public SDSlaveNode(){

    }


    public void startService(){
        //start dfs slave service.
        slaveIO = new SDSlaveIO();
        serviceName = SDSlaveService.class.getCanonicalName();
        try {
            SDSlaveService slaveService = new SDSlaveRMIService(this);
            registry = LocateRegistry.createRegistry(SDUtil.SALVE_RMIREGISTRY_PORT);
            registry.rebind(SDSlaveService.class.getCanonicalName(), slaveService);
            registry = LocateRegistry.getRegistry(SDUtil.masterAddress, SDUtil.MASTER_RMIRegistry_PORT);
            masterService = (SDMasterService) registry.lookup(SDMasterService.class.getCanonicalName());
            heartbeatService = Executors.newScheduledThreadPool(10);
            heartbeatService.scheduleAtFixedRate(new SDSlaveHeartbreatsJob(this.slaveIO, registry),
                    0, 1000, TimeUnit.SECONDS);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

        //TODO: should start map reduce service.
    }
}
