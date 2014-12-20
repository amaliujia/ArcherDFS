package Heartbeats;

import Master.SDSlave;
import Util.SDUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by amaliujia on 14-12-20.
 */
public class SDMasterHeartbeats implements Runnable{

    private ArrayList<SDSlave> slaveList;
    private DatagramSocket listener;
    private HashMap<Integer, Boolean> responderList;

    /**
     * Constructor for SDMaterHearbeats
     * @param list
     */
    public SDMasterHeartbeats(ArrayList<SDSlave> list){
        slaveList = list;
        responderList = new HashMap<Integer, Boolean>();
    }

    public void run() {

    }

    private void startListening(long currentTime) throws IOException {
        try {
            listener = new DatagramSocket(SDUtil.heatbeatsPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        //if UDP socket creates successfully
        responderList.clear();
        if(listener != null){

            //prepare buffer
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, 1024);

            while(true){
                //next timeout computation
                long interval = getCurrentTimeInMillionSeconds() - currentTime;
                int timeout = (int) (SDUtil.heartbeatsIntervalMillionSeconds - interval);
                if(timeout <= 0){
                    break;
                }

                //start listening
                try {
                    listener.setSoTimeout(timeout);
                    listener.receive(packet);
                    //TODO: what should I do now?
                    String receive = new String(packet.getData(), 0, packet.getLength());
                    String[] args = receive.split(" ");
                } catch (SocketTimeoutException e){
                    System.err.println("UDP socket time out");
                }
            }
            listener.close();
        }
    }

    private long getCurrentTimeInMillionSeconds(){
         Date currentData = new Date();
         return currentData.getTime();
    }
}
