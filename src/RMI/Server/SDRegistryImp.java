package RMI.Server;

import ArcherException.SDDuplicateService;
import ArcherException.SDServiceNotExist;
import RMI.Client.SDRemoteObjectReference;
import RMI.RMIBase.SDRegistry;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by amaliujia on 14-12-23.
 */
public class SDRegistryImp implements SDRegistry {
    // Singleton
    private  static final SDRegistryImp singleRegistry = new SDRegistryImp();
    private SDRegistryImp(){
        refs = new ConcurrentHashMap<String, SDRemoteObjectReference>();
    }
    public static SDRegistry getSharedRegistry() {
        return singleRegistry;
    }
    private ConcurrentHashMap<String, SDRemoteObjectReference> refs;

    /*public SDRegistryImp(){
        refs = new ConcurrentHashMap<String, SDRemoteObjectReference>();
    }*/

    /**
     * Look up a service name in registry.
     * @param serviceName
     * @return
     */
    @Override
    public SDRemoteObjectReference lookup(String serviceName) {
        SDRemoteObjectReference ref = refs.get(serviceName);
        return ref;
    }

    /**
     *
     * @param serviceName
     * @param ref
     * @throws SDDuplicateService
     */
    @Override
    public void bind(String serviceName, SDRemoteObjectReference ref) throws SDDuplicateService {
        if(refs.containsKey(serviceName)){
            throw new SDDuplicateService(ref.toString() + "  have been existed!");
        }else{
            refs.put(serviceName, ref);
        }
    }

    /**
     *
     * @param serviceName
     * @throws SDServiceNotExist
     */
    @Override
    public void unbind(String serviceName) throws SDServiceNotExist {
        if(!refs.containsKey(serviceName)){
            throw new SDServiceNotExist(serviceName + " does not exist");
        }else{
            refs.remove(serviceName);
        }
    }

    /**
     *
     * @param serviceName
     * @param ref
     * @throws SDDuplicateService
     *
     * is it necessary?
     */
    @Override
    public void rebind(String serviceName, SDRemoteObjectReference ref) throws SDDuplicateService {
        if(refs.containsKey(serviceName)){
            throw new SDDuplicateService(ref.toString() + "  have been existed!");
        }else{
            refs.put(serviceName, ref);
        }
    }

    /**
     *
     * @return List of service names of RemoteObjectReference
     */
    @Override
    public String[] getBindList() {
        Set<String> keys = refs.keySet();
        String[] results = new String[keys.size()];
        keys.toArray(results);
        return results;
    }
}
