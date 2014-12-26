package RMI.Server;

import ArcherException.SDConnectionException;
import RMI.Client.SDRemoteObjectReference;
import RMI.RMIBase.HKRMIMessage;
import RMI.RMIBase.SDRemoteConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * The <code>SDRMIRemoteServerJob</code> is used to handle
 * the remote invocation request from client. In order to
 * use thread pool, it implements the <code>Runnable</code>
 * interface
 */
public class SDRMIRemoteServerJob implements Runnable {

    private Socket socket;

    public SDRMIRemoteServerJob(Socket sock){
        socket = sock;
    }

    @Override
    /**
     *  3 basic functions: 1.lookup specific service name 2.list all service names 3.invoke specific functions
     */
    public void run(){
        HKRMIMessage msg = null;
        try {
            msg = receiveMsg(socket);
        }
        catch (IOException ex){
            System.err.println("ObjectInputStream IOException in SDRMIRemoteServerJob");
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex){
            System.err.println("ObjectInputStream ClassNotFoundException in SDRMIRemoteServerJob");
            ex.printStackTrace();
        }

        if (msg.getType() == HKRMIMessage.RMIMsgType.LOOKUP){
            try {
                this.lookupName(msg);
            }
            catch (SDConnectionException ex){
                ex.printStackTrace();
            }
        }
        else if (msg.getType() == HKRMIMessage.RMIMsgType.CALL){

        }
        else if (msg.getType() == HKRMIMessage.RMIMsgType.LIST){
            try {
                this.listFunction(msg);
            }
            catch (SDConnectionException ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     *
     * @param msg request information of RemoteObject
     * @return
     * @throws SDConnectionException
     */
    private boolean lookupName(HKRMIMessage msg) throws SDConnectionException{
        String serviceName =  msg.getServiceName();
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
    private boolean callFunction(HKRMIMessage msg) throws SDConnectionException{
        String serviceName =  msg.getServiceName();
        SDRemoteObjectReference service = SDRegistryImp.getSharedRegistry().lookup(serviceName);
        SDServerResponseConnection response = new SDServerResponseConnection(socket);
        SDRemoteObjectReference ror = null;
        if (service != null) {
            ror = new SDRemoteObjectReference(SDRMIRemoteServer.getLocalIP(), SDRMIRemoteServer.getListenPort(), serviceName);
            Method systemMethod = ((Object) ror).getClass().getMethod();  // T.B.D conflict with unmarshalling
        }
        HKRMIMessage respondMessage = new HKRMIMessage(ror, HKRMIMessage.RMIMsgType.RETURN);
        this.sendMsg(socket, respondMessage);
        return true;
    }

    private boolean listFunction(HKRMIMessage msg) throws SDConnectionException{
        String serviceName =  msg.getServiceName();
        SDServerResponseConnection response = new SDServerResponseConnection(socket);
        HKRMIMessage respondMessage = new HKRMIMessage(SDRegistryImp.getSharedRegistry().getBindList(), HKRMIMessage.RMIMsgType.RETURN);
        this.sendMsg(socket, respondMessage);
        return true;
    }

    /**
     *
     * @param socket Client
     * @return Receive a message from client socket. Return null if the incoming message is not RMIMessage.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public HKRMIMessage receiveMsg(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream inStream;
        Object inObj;

        inStream = new ObjectInputStream(socket.getInputStream());
        inObj = inStream.readObject();
        if (inObj instanceof HKRMIMessage) {
            HKRMIMessage msg = (HKRMIMessage) inObj;
            return msg;
        }
        return null;
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
