package Protocol.SlaveService;

import java.rmi.Remote;

/**
 * Created by amaliujia on 14-12-27.
 */
public interface SDSlaveService extends Remote {
    //read function that allows server (may other slaves) read files stored in local fs
    public byte[] read(long chunkID, long offset, long size);

    //write function that allows server (may other slaves) write fiels stored in local fs
    public boolean write(long chunkID, long offset, int size, byte[] data);

    //delete chunks from local fs
    public boolean delete(long chunkID);

}