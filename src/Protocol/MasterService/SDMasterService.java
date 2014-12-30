package Protocol.MasterService;

import FileSystem.Base.SDDFSFile;
import RMI.RMIBase.SDRemote;

/**
 * Created by amaliujia on 14-12-27.
 */
public interface SDMasterService extends SDRemote {
    //create or update nodes, actually it is a kind of heartbeats
    public void updateDataNode(String serviceName, String registryHost, int registryPort,
                               int chunkNumber, long timestamp, boolean logable);

    //delete a node
    public void deleteDataNode(String serviceName, boolean logable);
    //create file
    //TODO: replication here?
    public SDDFSFile createFile(String fileName, int re);

    //get file
    public SDDFSFile getFile(String fileName);

    //list files
    public SDDFSFile[] listFiles();

    //delete file
    public void deleteFile(String fileName);

    //TODO: create chunk here?
}

