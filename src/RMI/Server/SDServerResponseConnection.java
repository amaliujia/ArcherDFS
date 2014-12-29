package RMI.Server;

import ArcherException.SDConnectionException;
import RMI.Client.SDCommandConstants;
import RMI.Client.SDRemoteObjectReference;
import RMI.RMIBase.HKRMIMessage;
import RMI.RMIBase.SDRemoteConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.server.RemoteObject;

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



    /**
     *
     * @param serviceName request information of RemoteObject
     * @return
     * @throws SDConnectionException
     */
    public boolean lookupName(String serviceName) throws SDConnectionException{
        SDRemoteObjectReference service = SDRegistryImp.getSharedRegistry().lookup(serviceName);
        SDServerResponseConnection response = new SDServerResponseConnection(socket);
        SDRemoteObjectReference ror = null;
        if (service != null) {
            ror = new SDRemoteObjectReference(SDRMIRemoteServer.getLocalIP(), SDRMIRemoteServer.getListenPort(), serviceName);
        }
        HKRMIMessage respondMessage = new HKRMIMessage(ror, HKRMIMessage.RMIMsgType.LOOKUP);
        this.sendMsg(socket, respondMessage);
        return true;
    }

    /**
     *
     * @param msg
     * @return
     */
    public boolean callFunction(Object[] msg) throws SDConnectionException{
        String serviceName =  (String)msg[1];
        SDRemoteObjectReference service = SDRegistryImp.getSharedRegistry().lookup(serviceName);
        SDServerResponseConnection response = new SDServerResponseConnection(socket);
        SDRemoteObjectReference ror = null;
        if (service != null) {
            ror = new SDRemoteObjectReference(SDRMIRemoteServer.getLocalIP(), SDRMIRemoteServer.getListenPort(), serviceName);
            // Method systemMethod = ((Object) ror).getClass().getMethod();  // T.B.D conflict with unmarshalling
        }
        HKRMIMessage respondMessage = new HKRMIMessage(ror, HKRMIMessage.RMIMsgType.RETURN);
        return true;
    }

    public boolean listFunction() throws SDConnectionException{

        SDServerResponseConnection response = new SDServerResponseConnection(socket);
        HKRMIMessage respondMessage = new HKRMIMessage(SDRegistryImp.getSharedRegistry().getBindList(), HKRMIMessage.RMIMsgType.RETURN);
        this.sendMsg(socket, respondMessage);
        return true;
    }

    /**
     *
     * @return Receive a message from client socket. Return null if the incoming message is not RMIMessage.
     * @throws java.io.IOException
     * @throws ClassNotFoundException
     */
    public void process() throws IOException, ClassNotFoundException {
        Byte operationCode = in.readByte();

        if (operationCode == SDCommandConstants.LIST){
            try {
                this.listFunction();
            }
            catch (SDConnectionException ex){
                ex.printStackTrace();
            }
        }

        SDRemoteObjectReference ror = (SDRemoteObjectReference)in.readObject();
        String serviceName = (String)in.readObject();
        if (operationCode == SDCommandConstants.LOOKUP){
            try {
                this.lookupName(serviceName);
            }
            catch (SDConnectionException ex){
                ex.printStackTrace();
            }
        }
        Object[] msg = null;
        if (operationCode == SDCommandConstants.INVOKE){
            try {
                this.callFunction(msg);
            }
            catch (SDConnectionException ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     *
     * @param socket Client
     * @param msg Send msg to the client socket.
     */
    public void sendMsg(Socket socket, Object msg) {
        if (!(msg instanceof HKRMIMessage)) {
            return;
        }
        ObjectOutputStream out;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
