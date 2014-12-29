package RMI.Server;

import ArcherException.SDConnectionException;
import RMI.Client.SDRemoteObjectReference;
import RMI.RMIBase.HKRMIMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


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
        Object[] msg = null;
        SDServerResponseConnection msgHandler = null;
        try {
            msgHandler = new SDServerResponseConnection(socket);
        }
        catch(SDConnectionException ex){
            System.err.println("ObjectInputStream IOException in SDRMIRemoteServerJob");
            ex.printStackTrace();
        }
        /*
        catch (ClassNotFoundException ex){
            System.err.println("ObjectInputStream ClassNotFoundException in SDRMIRemoteServerJob");
            ex.printStackTrace();
        }
        */


    }



}
