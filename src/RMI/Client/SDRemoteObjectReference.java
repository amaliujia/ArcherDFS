package RMI.Client;

import ArcherException.SDConnectionException;

import java.io.Serializable;
import java.lang.reflect.Method;

public class SDRemoteObjectReference implements Serializable {

    private String address;

    private int port;

    private long id;

    private String className;

    public SDRemoteObjectReference(String address, int port, String className){
        this.port = port;
        this.className = className;
        this.address = address;
    }

    public SDRemoteObjectReference(String address, int port, String className, long id){
        this.port = port;
        this.className = className;
        this.address = address;
        this.id = id;
    }


    /**
     * Invoke a remote method. We use this function to communicate
     * with remote server, marshal the method and parameters, then
     * unmarshal the result. If something goes wrong throws an exception
     * @param method
     * @param methodHash
     * @param params
     * @return
     * @throws SDConnectionException
     * @throws ClassNotFoundException
     */
    public Object invoke(Method method, long methodHash, Object[] params)
            throws SDConnectionException, ClassNotFoundException {
       SDInvokeRemoteConnection connection = new SDInvokeRemoteConnection(address, port);
        Object result = connection.invoke(this, method, methodHash, params);
        return result;
    }

    public String getClassName(){
        return className;
    }

    public String toString(){
        return id + "[" + className + "@" + address + ":" + port + "]";
    }

}
