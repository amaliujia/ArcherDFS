package RMI.Client;

/**
 * Created by amaliujia on 14-12-22.
 */
public interface SDRegistry extends SDRemote{

    SDRemoteObjectReference lookup(String serviceName);
    void bind(String serviceName, SDRemoteObjectReference ref);
    void unbind(String serviceName);
    void rebind(String serviceName, SDRemoteObjectReference ref);

}
