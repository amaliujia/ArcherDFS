package RMI.Server;

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

    }
}
