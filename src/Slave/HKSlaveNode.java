package Slave;


import Heartbeats.HKSlaveHeartbeats;

import java.io.*;
import java.net.Socket;
/**
 * Created by kanghuang on 12/20/14.
 */
public class HKSlaveNode implements Runnable{
    private PrintWriter pw;
    private BufferedReader bs;
    private String masterAddress;
    private int masterPort;
    private Socket socket;
    HKSlaveHeartbeats heartBeat;
    public HKSlaveNode(String masterAddress, int masterPort){
        this.masterAddress = masterAddress;
        this.masterPort = masterPort;
    }
    public void connect(){
        try{
            socket = new Socket(masterAddress, masterPort);
            bs = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.heartBeat = new HKSlaveHeartbeats(socket.getLocalPort());
        }
        catch(IOException ex) {
            System.out.println("MasterAddress or MasterPort is wrong");
        }
        System.out.println("Connection success");
    }

    public void disconnect(){
        try{
            bs.close();
            pw.close();
            socket.close();
        }
        catch (IOException ex)
        {
            System.out.println("disConnection error\n");
        }
        System.out.println("disConnection success");
    }

    /**
     * basic services
     * @throws IOException
     */
    public void startService() throws IOException{
        String command = bs.readLine();
        if (command == null){
            System.out.println("Interrupt!");
            System.exit(1);
        }
        if (command.equals("Alive?")){
            new Thread(this.heartBeat).start();
            System.out.println("heartBeat");
        }
    }

    /**
     *  keep working
     */
    @Override
    public void run() {
        while(true){
            try {
                this.startService();
            } catch (IOException e) {
                System.out.println("socket connection error");
                e.printStackTrace();
            }

        }
    }
}
