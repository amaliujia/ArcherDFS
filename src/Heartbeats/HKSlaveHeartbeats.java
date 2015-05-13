package Heartbeats;

import Util.SDUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author amaliujia
 * @author kanghuang
 */
public class HKSlaveHeartbeats implements Runnable {
    private DatagramSocket ds;

    private InetAddress heartBeatAddress;

    private int tcpPort;

    private int count = 0;

    //private Logger logger = LoggerFactory.getLogger(HKSlaveHeartbeats.class);

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
           // logger.error("UDP connection error in client heart beats!");
            //System.out.println("UDP connection error!");
        }
        DatagramPacket dp_send= new DatagramPacket(ack.getBytes(),ack.length(), heartBeatAddress, SDUtil.heatbeatsPort);
        try {
            ds.send(dp_send);
        } catch (IOException e) {
            //System.out.println("Slave fails to send");
          //  logger.error("Slave fails to send in client heart beats!");
            e.printStackTrace();
        }

    }
}
