package RMI;

import Util.SDUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.rmi.server.RemoteObject;
import java.util.HashMap;
import java.net.Socket;

/**
 * Created by laceyliu on 12/21/14.
 */
public class SDRMIRegistry {
    private HashMap<String, SDRemoteObjectReference> warehouse;
    private int port = 16643;

    public SDRMIRegistry(){
        warehouse = new HashMap<String, SDRemoteObjectReference>();
    }

    public SDRMIRegistry(int port){
        warehouse = new HashMap<String, SDRemoteObjectReference>();
        this.port = port;
    }

    public static void main(String args[]){
        SDRMIRegistry registry = new SDRMIRegistry(SDUtil.RMIRegistryPort);
        System.out.println("RMIRegistry started");

        try {
            ServerSocket serverSocketsocket = new ServerSocket(registry.port);
            Socket sock = null;
            ObjectInputStream input = null;
            Object inputObj = null;
            Class<?> c = null;

            while(true){
                sock = serverSocketsocket.accept();
                input = new ObjectInputStream(sock.getInputStream());
                inputObj = input.readObject();
                c = inputObj.getClass();

                // get a client lookup, then send back the ror
                //TODO: deinfe RMI methods
                if(c.equals(Class.forName("Dummy"))){

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
