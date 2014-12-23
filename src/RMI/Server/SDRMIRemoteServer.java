package RMI.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SDRMIRemoteServer implements Runnable{

    // server port
    private int port;

    //server address
    private String address;

    //thread pool
    private ExecutorService threadsPool;

    //listening
    ServerSocket sock;

    public SDRMIRemoteServer(String address, int port, int size){
        this.address = address;
        this.port = port;
        threadsPool = Executors.newFixedThreadPool(size);
    }


    @Override
    public void run() {
        try {
            sock = new ServerSocket(port);
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
