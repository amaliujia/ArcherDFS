package FileSystem.Slave;

import FileSystem.Util.SDDFSConstants;
import Protocol.DFS.MasterService.SDMasterService;
import Protocol.DFS.SlaveService.SDSlaveService;
import Util.SDUtil;

import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

/**
 * @author amaliujia
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
            masterService.heartbeat(serviceName, SDUtil.getlocalHost(), SDDFSConstants.DEFAULT_SLAVE_REGISTRY_PORT,
                                    slaveIO.getChunkNumber(), true);
        } catch (RemoteException e) {
            System.err.println("master node error");
        } catch (NotBoundException e) {
            System.err.println("master service not found");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
