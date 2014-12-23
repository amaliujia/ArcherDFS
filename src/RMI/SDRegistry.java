package RMI;

/**
 * Created by amaliujia on 14-12-22.
 */
public interface SDRegistry extends Remote{

    SDRemoteObjectReference lookup(String serviceName);
    void bind(String serviceName, SDRemoteObjectReference ref);
    void unbind(String serviceName);
    void rebind(String serviceName, SDRemoteObjectReference ref);

}
