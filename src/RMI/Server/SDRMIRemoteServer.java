package RMI.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SDRMIRemoteServer implements Runnable{

    // server port
    private static int listenPort;

    //server address
    private static String address;

    //thread pool
    private ExecutorService threadsPool;

    //listening
    private ServerSocket sock;

    public SDRMIRemoteServer(String address, int port, int size){
        this.address = address;
        this.listenPort = port;
        threadsPool = Executors.newFixedThreadPool(size);
    }

    public static String getLocalIP(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException ex){
            System.err.println("UnknownHost");
            ex.printStackTrace();
        }
        return null;
    }

    public static int getListenPort(){
            return listenPort;
    }
    @Override
    public void run() {
        try {
            sock = new ServerSocket(this.listenPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            Socket socket;
            try {
                socket = sock.accept();
            } catch (IOException e) {
                continue;
            }
            threadsPool.execute(new SDRMIRemoteServerJob(socket));
        }
    }
}
