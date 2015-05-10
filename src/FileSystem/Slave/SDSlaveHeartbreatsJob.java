package FileSystem.Slave;

import FileSystem.Util.SDDFSConstants;
import Master.SDSlave;
import Protocol.DFS.MasterService.SDMasterService;
import Protocol.DFS.SlaveService.SDSlaveService;
import Slave.SDSlaveNode;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

/**
 * Created by amaliujia on 15-1-2.
 */
public class SDSlaveHeartbreatsJob implements Runnable{

    private SDSlaveIO slaveIO;

    private Registry registry;

    public SDSlaveHeartbreatsJob(SDSlaveIO IO, Registry registry){
        this.registry = registry;
        this.slaveIO = IO;
    }

    public void run() {
        try {
            String serviceName = SDSlaveService.class.getCanonicalName();
            SDMasterService masterService = (SDMasterService)
                    registry.lookup(SDMasterService.class.getCanonicalName());
            masterService.heartbeat(serviceName, "localhost", SDDFSConstants.DEFAULT_SLAVE_REGISTRY_PORT,
                                    slaveIO.getChunkNumber(), true);
        } catch (RemoteException e) {
            System.err.println("master node error");
        } catch (NotBoundException e) {
            System.err.println("master service not found");
        }
    }
}
