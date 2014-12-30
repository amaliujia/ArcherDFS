package FileSystem.Master;

import FileSystem.Base.SDDFSFile;
import Protocol.MasterService.SDMasterService;

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
    public void updateDataNode(String serviceName, String registryHost, int registryPort,
                               int chunkNumber, long timestamp, boolean logable) {
        index.updateDataNode(serviceName, registryHost, registryPort, chunkNumber, timestamp, logable);
    }

    @Override
    public void deleteDataNode(String serviceName, boolean logable) {
        index.removeDataNode(serviceName, logable);
    }

    @Override
    public SDDFSFile createFile(String fileName, int re) {
        return index.createFile(fileName, re, true);
    }

    @Override
    public SDDFSFile getFile(String fileName) {
        return index.getFile(fileName);
    }

    @Override
    public SDDFSFile[] listFiles() {
        return index.listFiles();
    }

    @Override
    public void deleteFile(String fileName) {
        index.deleteFile(fileName, true);
    }
}
