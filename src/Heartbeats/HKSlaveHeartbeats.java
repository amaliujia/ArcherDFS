package Heartbeats;

import Util.SDUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by kanghuang on 12/20/14.
 */
public class HKSlaveHeartbeats implements Runnable {
    private DatagramSocket ds;
    private InetAddress heartBeatAddress;
    private int tcpPort;
    private int count = 0;
    public HKSlaveHeartbeats(int tcpPort){
        try {
            heartBeatAddress = InetAddress.getByName(SDUtil.masterAddress);
            this.tcpPort = tcpPort;
        }
        catch (UnknownHostException e) {
            System.out.println("UnknownHost");
            e.printStackTrace();
        }
    }

    public void run(){
        heartBeatsResponse();
        System.out.println(++count);
    }

    /**
     *  heartbeat response
     */
    public void heartBeatsResponse() {
        String ack = "" + this.tcpPort;
        try {
            ds = new DatagramSocket();
            ds.connect(heartBeatAddress, SDUtil.heatbeatsPort);
        }
        catch (IOException ex){
            System.out.println("UDP connection error!");
        }
        DatagramPacket dp_send= new DatagramPacket(ack.getBytes(),ack.length(), heartBeatAddress, SDUtil.heatbeatsPort);
        try {
            ds.send(dp_send);
        } catch (IOException e) {
            System.out.println("Slave fails to send");
            e.printStackTrace();
        }

    }
}
