package Heartbeats;

import Util.SDUtil;

import java.io.IOException;
import java.net.*;

/**
 * Created by kanghuang on 12/20/14.
 */
public class HKSlaveHeartbeats implements Runnable {
    private DatagramSocket ds;
    private InetAddress heartBeatAddress;
    public HKSlaveHeartbeats(){
        try {
            heartBeatAddress = InetAddress.getByName(SDUtil.masterAddress);
        }
        catch (UnknownHostException e) {
            System.out.println("UnknownHost");
            e.printStackTrace();
        }
    }

    public void run(){

    }
    public void heartBeatsResponse() {
        String ack = "alive\n";
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
