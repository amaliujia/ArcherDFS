package FileSystem.Slave;

import Protocol.MasterService.SDMasterService;

import java.net.UnknownHostException;
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


    @Override
    public void run() {
//        try {
//            SDMasterService masterService = (SDMasterService)
//                    registry.lookup(SDMasterService.class.getCanonicalName());
//            masterService.heartbeat();
//        } catch (RemoteException e) {
//            System.err.println("master node error");
//        } catch (NotBoundException e) {
//            System.err.println("master service not found");
//        } catch (UnknownHostException e) {
//            System.err.println("can't resolve hostname");
//        }
    }
}
