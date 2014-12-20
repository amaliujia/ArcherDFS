package Slave;


import Heartbeats.HKSlaveHeartbeats;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
    public HKSlaveNode(String masterAddress, int masterPort){
        this.masterAddress = masterAddress;
        this.masterPort = masterPort;
        HKSlaveHeartbeats heartBeat = new HKSlaveHeartbeats();
    }
    public void connect(){
        try{
            socket = new Socket(masterAddress, masterPort);
            bs = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
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

    public void start_Service(){

    }


    @Override
    public void run() {

    }
}
