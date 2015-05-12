package Protocol.DFS.MasterService;

import FileSystem.Base.SDDFSFile;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by amaliujia on 14-12-27.
 */
public interface SDMasterService extends Remote {

    public void heartbeat(String serviceName, String registryHost, int registryPort, int chunkNumber, boolean logable)
                                        throws RemoteException;
    //create or update nodes, actually it is a kind of heartbeats
    public void updateDataNode(String serviceName, String registryHost, int registryPort,
                               int chunkNumber, long timestamp, boolean logable) throws RemoteException;

    //delete a node
    public void deleteDataNode(String serviceName, boolean logable) throws RemoteException;

    //create file
    public SDDFSFile createFile(String fileName, int re, boolean logable) throws RemoteException;

    //get file
    public SDDFSFile getFile(String fileName) throws RemoteException;

    //list files
    public SDDFSFile[] listFiles() throws RemoteException;

    //delete file
    public void deleteFile(String fileName, boolean logable) throws RemoteException;

    //distribute file
    public void distributeFile(long fileID, boolean logable) throws RemoteException;

    //distribute file
    public void distributeFile(String filename, boolean logable) throws RemoteException;

    //
}

