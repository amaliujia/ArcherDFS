package FileSystem.Base;

import java.io.Serializable;

/**
 * @author amaliujia
 */
public class SDDFSNode implements Serializable, Comparable<SDDFSNode>{

    private String serviceName;

    private String registryHost;

    private int registryPort;

    private int chunkerNumber;

    private long timestamp;


    public SDDFSNode(String serviceName, String registryHost, int registryPort){
        this.serviceName = serviceName;
        this.registryHost = registryHost;
        this.registryPort = registryPort;
        this.chunkerNumber = 0;
        this.timestamp = 0;
    }


    public String getRegistryHost() {
        return registryHost;
    }

    public int getRegistryPort() {
        return registryPort;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getChunkNumber(){
        return chunkerNumber;
    }

    public void setChunkNumber(int chunkNumber){
        this.chunkerNumber = chunkNumber;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    @Override
    public String toString(){
        return "service name: " + getServiceName() + " registry host: " +
                getRegistryHost() + " registry port" + getRegistryPort();
    }

    @Override
    public int hashCode(){
        return getServiceName().hashCode();
    }

    @Override
    public boolean equals(Object node){
        if(node instanceof SDDFSNode){
            return hashCode() == node.hashCode();
        } else {
            return false;
        }
    }

    public int compareTo(SDDFSNode dfsNode) {
        return getServiceName().hashCode() - dfsNode.getServiceName().hashCode();
    }
}
