package RMI.RMIBase;

import ArcherException.SDDuplicateService;
import ArcherException.SDServiceNotExist;
import RMI.Client.SDRemoteObjectReference;

/**
 * Created by amaliujia on 14-12-22.
 */
public interface SDRegistry extends SDRemote {

    //look up a service in server
    SDRemoteObjectReference lookup(String serviceName);

    //bind a service in server
    void bind(String serviceName, SDRemoteObjectReference ref)
            throws SDDuplicateService;

    //unbind a service from server
    void unbind(String serviceName)
            throws SDServiceNotExist;

    //rebind a server in server
    void rebind(String serviceName, SDRemoteObjectReference ref)
            throws SDDuplicateService;

    //return registered methodName
    String[] getBindList();
}
