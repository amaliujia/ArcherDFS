package RMI;

import ArcherException.SDConnectionException;

/**
 * Created by amaliujia on 14-12-23.
 */
public class SDInvokeRemoteConnection extends SDRemoteConnection {
    public SDInvokeRemoteConnection(String address, int port) throws SDConnectionException {
        super(address, port);
    }
}
