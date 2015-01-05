package FileSystem.Master;

import FileSystem.Base.SDDFSFile;
import FileSystem.Index.SDDFSIndex;
import Protocol.DFS.MasterService.SDMasterService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by amaliujia on 14-12-28.
 */
public class SDMasterRMIService extends UnicastRemoteObject implements SDMasterService{

    private SDDFSIndex index;

    public SDMasterRMIService(SDDFSIndex index) throws RemoteException {
        super();
        this.index = index;
    }

    @Override
    public void heartbeat(String serviceName, String registryHost, int registryPort, int chunkNumber, boolean logable)
            throws RemoteException {
        index.updateDataNode(serviceName, registryHost, registryPort, chunkNumber, System.currentTimeMillis(), logable);
    }

    @Override
    public void updateDataNode(String serviceName, String registryHost, int registryPort,
                               int chunkNumber, long timestamp, boolean logable) throws RemoteException {
        index.updateDataNode(serviceName, registryHost, registryPort, chunkNumber, timestamp, logable);
    }

    @Override
    public void deleteDataNode(String serviceName, boolean logable) throws RemoteException {
        index.removeDataNode(serviceName, logable);
    }

    @Override
    public SDDFSFile createFile(String fileName, int re, boolean logable) throws RemoteException {
        return index.createFile(fileName, re, logable);
    }

    @Override
    public SDDFSFile getFile(String fileName) throws RemoteException {
        return index.getFile(fileName);
    }

    @Override
    public SDDFSFile[] listFiles() throws RemoteException {
        return index.listFiles();
    }

    @Override
    public void deleteFile(String fileName, boolean logable) throws RemoteException {
        index.deleteFile(fileName, logable);
    }

    @Override
    public void distributeFile(long fileID, boolean logable) throws RemoteException {
        index.distributeFile(fileID, logable);
    }

    public void distributeFile(String filename, boolean logable) throws RemoteException {
        long fileID = index.getFileID(filename);
        if(fileID == -1){
            return;
        }
        index.distributeFile(fileID, logable);
    }
}
