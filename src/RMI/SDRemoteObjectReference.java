package RMI;

import java.io.Serializable;

/**
 * Created by laceyliu on 12/21/14.
 */
public class SDRemoteObjectReference implements Serializable{
    private static final long serialVersionUID = -4362993247531924695L;
    private String address;
    private int port;
    private String remoteInterfaceName;
    private String objName;
    private String stubURL;

    public SDRemoteObjectReference(String ip, int port, String remoteInterfaceName,
                                   String objName, String stubURL) {
        this.address = ip;
        this.port = port;
        this.remoteInterfaceName = remoteInterfaceName;
        this.objName = objName;
        this.stubURL = stubURL;
        this.objName = objName;
    }
        public String getIpAddr() {
            return address;
        }

        public void setIpAddr(String ipAddr) {
            this.address = ipAddr;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getRemoteInterfaceName() {
            return remoteInterfaceName;
        }

        public void setRemoteInterfaceName(String remoteInterfaceName) {
            this.remoteInterfaceName = remoteInterfaceName;
        }

        public String getObjName() {
            return objName;
        }

        public void setObjName(String objName) {
            this.objName = objName;
        }

    //TODO: localise function

    //TODO: downloadStub function
}
