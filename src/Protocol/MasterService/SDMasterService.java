package Protocol.MasterService;

import RMI.RMIBase.SDRemote;

/**
 * Created by amaliujia on 14-12-27.
 */
public interface SDMasterService extends SDRemote {
    //create file


    //get file


    //list files


    //delete file
    public boolean deleteFile(long fileID);
}

