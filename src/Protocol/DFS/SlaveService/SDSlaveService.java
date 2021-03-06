package Protocol.DFS.SlaveService;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by amaliujia on 14-12-27.
 */
public interface SDSlaveService extends Remote {
    //read function that allows server (may other slaves) read files stored in local fs
     byte[] read(long chunkID, long offset, int size) throws IOException, RemoteException;

    //write function that allows server (may other slaves) write fiels stored in local fs
     boolean write(long chunkID, long offset, int size, byte[] data) throws RemoteException, IOException;

    //delete chunks from local fs
     boolean delete(long chunkID) throws RemoteException;

    //add chunk
     boolean append(long chunkID, int size,  byte[] data) throws RemoteException;

    //read offset of lines in chunk
    long[] offsetOfLinesInChunk(long chunkID) throws RemoteException;
}
