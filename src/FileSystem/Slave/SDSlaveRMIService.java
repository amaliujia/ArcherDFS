package FileSystem.Slave;

import Master.SDSlave;
import Protocol.SlaveService.SDSlaveService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by amaliujia on 14-12-29.
 */
public class SDSlaveRMIService extends UnicastRemoteObject implements SDSlaveService {

    private SDSlave slave;

    protected SDSlaveRMIService() throws RemoteException {
    }

    @Override
    public byte[] read(long chunkID, long offset, long size) {
        return new byte[0];
    }

    @Override
    public boolean write(long chunkID, long offset, int size, byte[] data) {
        return false;
    }

    @Override
    public boolean delete(long chunkID) {
        return false;
    }
}
