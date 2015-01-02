package Protocol.MasterService;

import FileSystem.Base.SDDFSFile;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by amaliujia on 14-12-27.
 */
public interface SDMasterService extends Remote {

    public void heartbeat(String serviceName, String registryHost, int registryPort, int chunkNumber)
                                        throws RemoteException;
    //create or update nodes, actually it is a kind of heartbeats
    public void updateDataNode(String serviceName, String registryHost, int registryPort,
                               int chunkNumber, long timestamp, boolean logable) throws RemoteException;

    //delete a node
    public void deleteDataNode(String serviceName, boolean logable) throws RemoteException;
    //create file
    //TODO: replication here?
    public SDDFSFile createFile(String fileName, int re) throws RemoteException;

    //get file
    public SDDFSFile getFile(String fileName) throws RemoteException;

    //list files
    public SDDFSFile[] listFiles() throws RemoteException;

    //delete file
    public void deleteFile(String fileName) throws RemoteException;

    //TODO: create chunk here?
}

