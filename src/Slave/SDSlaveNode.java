package Slave;

import FileSystem.Slave.SDSlaveIO;
import FileSystem.Slave.SDSlaveRMIService;
import Protocol.MasterService.SDMasterService;
import Util.SDUtil;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by amaliujia on 14-12-29.
 */
public class SDSlaveNode {

    public SDSlaveIO slaveIO;

    private Registry registry;

    private final String serviceName = "SlaveRMIService";

    private SDMasterService masterService;

    public SDSlaveNode(){

    }


    public void startService(){
        slaveIO = new SDSlaveIO();
        try {
           // SDSlaveRMIService sdSlaveRMIService = new SDSlaveRMIService(this);
           // registry = LocateRegistry.getRegistry();
           // registry.rebind(serviceName, sdSlaveRMIService);
            registry = LocateRegistry.getRegistry(SDUtil.masterAddress, SDUtil.MASTER_RMIRegistry_PORT);
            masterService = (SDMasterService) registry.lookup(SDMasterService.class.getCanonicalName());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
