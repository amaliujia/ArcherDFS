package Heartbeats;

import Master.SDSlave;
import Util.SDUtil;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by amaliujia on 14-12-20.
 */
public class SDMaterHeartbeats implements Runnable{

    private ArrayList<SDSlave> slaveList;
    private DatagramSocket listener;

    public SDMaterHeartbeats(ArrayList<SDSlave> list){
        slaveList = list;
    }

    public void run() {

    }

    private void startListening(){
        try {
            listener = new DatagramSocket(SDUtil.heatbeatsPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
