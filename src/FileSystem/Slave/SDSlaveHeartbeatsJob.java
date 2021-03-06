package FileSystem.Slave;

import FileSystem.Util.SDDFSConstants;
import Protocol.DFS.MasterService.SDMasterService;
import Util.SDUtil;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

/**
 * @author amaliujia
 */
public class SDSlaveHeartbeatsJob implements Runnable{

    private SDSlaveIO slaveIO;

    private Registry registry;

    public SDSlaveHeartbeatsJob(SDSlaveIO IO, Registry registry){
        this.registry = registry;
        this.slaveIO = IO;
    }

    public void run() {
        System.out.println("Heart beat");
        try {
            SDMasterService masterService = (SDMasterService)
                    registry.lookup(SDMasterService.class.getCanonicalName());
            masterService.heartbeat("slave", "localhost", SDUtil.SALVE_RMIREGISTRY_PORT,
                                    slaveIO.getChunkNumber(), true);
        } catch (RemoteException e) {
            System.err.println("master node error");
        } catch (NotBoundException e) {
            System.err.println("master service not found");
        }
    }
}
