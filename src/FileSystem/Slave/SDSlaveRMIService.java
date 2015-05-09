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

    public byte[] read(long chunkID, long offset, int size) throws IOException, RemoteException {
        return slave.slaveIO.read(chunkID, offset, size);
    }

    public boolean write(long chunkID, long offset, int size, byte[] data) throws IOException, RemoteException {
        return slave.slaveIO.write(chunkID, offset, (int)size, data);
    }


    public boolean delete(long chunkID) throws RemoteException {
        return slave.slaveIO.delete(chunkID);
    }

    public boolean append(long chunkID, int size, byte[] data) throws RemoteException {
        return slave.slaveIO.append(chunkID, size, data);
    }


}
