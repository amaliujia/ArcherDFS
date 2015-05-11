package FileSystem.Slave;

import Protocol.DFS.SlaveService.SDSlaveService;
import Slave.SDSlaveNode;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by amaliujia on 14-12-29.
 */
public class SDSlaveRMIService extends UnicastRemoteObject implements SDSlaveService {
    private SDSlaveNode slave;

    public SDSlaveRMIService() throws RemoteException {
    }

    public SDSlaveRMIService(SDSlaveNode node) throws RemoteException {
        super();
        this.slave = node;
    }

    /**
     * Read semantics.
     * @param chunkID
     *          target chunkID.
     * @param offset
     *          offset of target chunk.
     * @param size
     *          read requirement size.
     * @return
     *          required data.
     * @throws IOException
     *          throws when cannot read.
     * @throws RemoteException
     *          throws when RMI service fail.
     */
    public byte[] read(long chunkID, long offset, int size) throws IOException, RemoteException {
        return slave.slaveIO.read(chunkID, offset, size);
    }

    /**
     * Write semantics
     * @param chunkID
     *          target chunkID.
     * @param offset
     *          offset of target chunk.
     * @param size
     *          write data size.
     * @param data
     *          data to be written.
     * @return
     *          if write success.
     * @throws IOException
     *          throws when cannot write.
     * @throws RemoteException
     *          throws when RMI service fail.
     */
    public boolean write(long chunkID, long offset, int size, byte[] data) throws IOException, RemoteException {
        return slave.slaveIO.write(chunkID, offset, (int)size, data);
    }

    /**
     * Delete semantics.
     * @param chunkID
     *        target chunkID.
     * @return
     *         if complete.
     * @throws RemoteException
     *          throws when RMI service fail.
     */
    public boolean delete(long chunkID) throws RemoteException {
        return slave.slaveIO.delete(chunkID);
    }

    /**
     * Append sematics. This operation is special in dfs, used to add a new chunk.
     * @param chunkID
     *         target chunkID.
     * @param size
     *         append data size.
     * @param data
     *          data to be append.
     * @return
     *          if success.
     * @throws RemoteException
     *          throws when RMI service fail.
     */
    public boolean append(long chunkID, int size, byte[] data) throws RemoteException {
        return slave.slaveIO.append(chunkID, size, data);
    }


}
