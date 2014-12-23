package RMI.Server;

import ArcherException.SDConnectionException;
import RMI.RMIBase.SDRemoteConnection;

/**
 * Created by amaliujia on 14-12-23.
 */
public class SDServerResponseConnection extends SDRemoteConnection {
    public SDServerResponseConnection(String address, int port) throws SDConnectionException {
        super(address, port);
    }


}
