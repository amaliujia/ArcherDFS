package Protocol.MasterService;

import FileSystem.SDDFSFile;
import RMI.RMIBase.SDRemote;

/**
 * Created by amaliujia on 14-12-27.
 */
public interface SDMasterService extends SDRemote {
    //create file
    //TODO: replication here?
    public SDDFSFile createFile(String fileName);

    //get file
    public SDDFSFile getFile(String fileName);

    //list files
    public SDDFSFile[] listFiles();

    //delete file
    public boolean deleteFile(String fileName);

    //TODO: create chunk here?
}

