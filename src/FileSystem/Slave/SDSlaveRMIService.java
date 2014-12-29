package FileSystem.Slave;

import FileSystem.Util.SDDFSConstants;
import Master.SDSlave;
import Protocol.SlaveService.SDSlaveService;
import RMI.Client.SDCommandConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

/**
 * Created by amaliujia on 14-12-29.
 */
public class SDSlaveRMIService extends UnicastRemoteObject implements SDSlaveService {

    private SDSlave slave;

    protected SDSlaveRMIService() throws RemoteException {
    }

    @Override
    public byte[] read(long chunkID, long offset, int size) throws IOException {
        File file = new File(getChunkPath(chunkID));
        if(file.exists()){
            RandomAccessFile rFile = new RandomAccessFile(file, "r");
            rFile.seek(offset);
            byte[] readBuffer = new byte[size];
            int len = rFile.read(readBuffer, 0, size);
            if(len > 0){
                byte[] data = Arrays.copyOf(readBuffer, len);
                rFile.close();
                return data;
            }else{
                return null;
            }
        }

        return null;
    }

    @Override
    public boolean write(long chunkID, long offset, int size, byte[] data) throws IOException {
        File file = new File(getChunkPath(chunkID));
        if(file.exists()){
            RandomAccessFile rFile = new RandomAccessFile(file, "w");
            rFile.seek(offset);
            rFile.write(data, 0, Math.min(size, data.length));
            rFile.close();
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(long chunkID) {
        File file = new File(getChunkPath(chunkID));
        if(file.exists()){
            file.delete();
            return true;
        }
        return false;
    }


    private String getChunkPath(long chunkID){
        return SDDFSConstants.DATA_DIR + System.getProperty("file.separator") + SDDFSConstants.CHUNK_PREFIX +
                chunkID + '.' + SDDFSConstants.CHUNK_SUFFIX;
    }

}
