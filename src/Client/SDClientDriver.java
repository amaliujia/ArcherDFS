package Client;

import Protocol.DFS.MasterService.SDMasterService;
import Util.SDUtil;

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

    /**
     * Choose the right service function to execute.
     * @param method function name to be executed.
     * @param args arguments for invoked function
     */
    public void lookup(String method, Object[] args){
        if(method.equals("create")){
            try {
                masterService.createFile((String) args[0], (Integer) args[1], true);
                System.err.println("Client received");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }else if(method.equals("distributefile")){
            try {
                masterService.distributeFile((String) args[0], true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
