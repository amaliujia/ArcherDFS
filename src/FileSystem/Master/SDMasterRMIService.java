package FileSystem.Master;

import FileSystem.Base.SDDFSFile;
import FileSystem.Index.SDDFSIndex;
import MapReduce.MapReduceIO.SDDFSDataBlock;
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

    /**
     * Heart beat service. Provide slave information report function.
     * @param serviceName
     *                  slave service name.
     * @param registryHost
     *                  slave registry host.
     * @param registryPort
     *                  slave registry port.
     * @param chunkNumber
     *                  slave chunk number.
     * @param logable
     *                  if to log.
     * @throws RemoteException
     *              throws when Remote error happen.
     */
    public void heartbeat(String serviceName, String registryHost, int registryPort, int chunkNumber, boolean logable)
            throws RemoteException {
        index.updateDataNode(serviceName, registryHost, registryPort, chunkNumber, System.currentTimeMillis(), logable);
    }

    /**
     * Update data nodes in index
     * @param serviceName
     *                  slave service name.
     * @param registryHost
     *                  slave registry host.
     * @param registryPort
     *                  slave registry port.
     * @param chunkNumber
     *                  slave chunk number.
     * @param timestamp
     *                  time when this operation happens.
     * @param logable
     *                  If to log.
     * @throws RemoteException
     *                  throws when Remote error happen.
     */
    
    public void updateDataNode(String serviceName, String registryHost, int registryPort,
                               int chunkNumber, long timestamp, boolean logable) throws RemoteException {
        index.updateDataNode(serviceName, registryHost, registryPort, chunkNumber, timestamp, logable);
    }


    /**
     * Once data node lose control, delete it from index and redistribute files.
     * @param serviceName
     *                  slave service name.
     * @param logable
     *                  If to log.
     * @throws RemoteException
     *                  throws when Remote error happen.
     */
    
    public void deleteDataNode(String serviceName, boolean logable) throws RemoteException {
        index.removeDataNode(serviceName, logable);
    }

    /**
     * Create file in ArcherDFS.
     * @param fileName
     *              Filename of this file.
     * @param re
     *              Number of replication of a file.
     * @param logable
     *              If to log.
     * @return
     *          return reference of a SDDFSFile.
     * @throws RemoteException
     *              throws when Remote error happen.
     */
    
    public SDDFSFile createFile(String fileName, int re, boolean logable) throws RemoteException {
        System.err.println("get in master rmi service");
        return index.createFile(fileName, re, logable);
    }

    /**
     * Get file from ArcherDFS.
     * @param fileName
     *          Filename of this file.
     * @return
     *          return reference of a SDDFSFile.
     * @throws RemoteException
     *          throws when Remote error happen.
     */
    
    public SDDFSFile getFile(String fileName) throws RemoteException {
        return index.getFile(fileName);
    }

    /**
     * List all files in ArcherDFS.
     * @return
     *          Array of SDDFSFiles.
     * @throws RemoteException
     *           throws when Remote error happen.
     */
    
    public SDDFSFile[] listFiles() throws RemoteException {
        return index.listFiles();
    }

    /**
     * Delete file from ArcherDFS.
     * @param fileName
     *                  slave file name.
     * @param logable
     *                  If to log.
     * @throws RemoteException
     *                  throws when Remote error happen.
     */
    
    public void deleteFile(String fileName, boolean logable) throws RemoteException {
        index.deleteFile(fileName, logable);
    }

    /**
     * Distribute file into data nodes.
     * @param fileID
     *          File ID.
     * @param logable
     *          If to log.
     * @throws RemoteException
     *          throws when Remote error happen.
     */
    
    public void distributeFile(long fileID, boolean logable) throws RemoteException {
        index.distributeFile(fileID, logable);
    }

    /**
     * Distribute file into data nodes.
     * @param filename
     *                  slave file name.
     * @param logable
     *          If to log.
     * @throws RemoteException
     *          throws when Remote error happen.
     */
    public void distributeFile(String filename, boolean logable) throws RemoteException {
        long fileID = index.getFileID(filename);
        if(fileID == -1){
            return;
        }
        index.distributeFile(fileID, logable);
    }

    /**
     * Split file into lines, and return offsets of lines.
     * @param filename
     *            Filename in DFS.
     * @return
     *          offset array of lines
     * @throws RemoteException
     *          throws when Remote error happen.
     */
    public long[] splitFile(String filename) throws RemoteException {
        return new long[0];
    }

    public SDDFSDataBlock[] getBlocks(String filename, int size, long offset) {
        return index.getBlocks(filename, size, offset);
    }


}
