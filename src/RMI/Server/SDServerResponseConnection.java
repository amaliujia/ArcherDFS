package RMI.Server;

import ArcherException.SDConnectionException;
import RMI.RMIBase.SDRemoteConnection;

import java.net.Socket;

/**
 * The <code>SDServerResponseConnection</code> handle the RMI request
 * from the client and return values back to client.
 *
 */
public class SDServerResponseConnection extends SDRemoteConnection {

    public SDServerResponseConnection(String address, int port) throws SDConnectionException {
        super(address, port);
    }

    public SDServerResponseConnection(Socket socket) throws SDConnectionException {
        super(socket);
    }
}
