package RMI;

import java.io.Serializable;

/**
 * Created by amaliujia on 14-12-22.
 */
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

}
