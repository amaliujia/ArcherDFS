package Client;

import Protocol.MasterService.SDMasterService;
import Util.SDUtil;

import java.beans.FeatureDescriptor;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by amaliujia on 15-1-3.
 */
public class SDClientDriver {

    private Registry registry;

    private SDMasterService masterService;

    public SDClientDriver(){
        try {
            registry = LocateRegistry.getRegistry(SDUtil.masterAddress, SDUtil.MASTER_RMIRegistry_PORT);
            masterService = (SDMasterService)registry.lookup(SDMasterService.class.getCanonicalName());
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void lookup(String method, Object[] args){
        if(method == "create"){
            try {
                masterService.createFile((String)args[0], (Integer)args[1]);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
