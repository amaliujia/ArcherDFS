package Heartbeats;

import Master.SDSlave;
import Util.SDUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * Created by amaliujia on 14-12-20.
 */
public class SDMasterHeartbeats extends TimerTask implements Runnable{

    private ArrayList<SDSlave> slaveList;
    private DatagramSocket listener;
    private HashMap<Integer, Boolean> responderList;
    private HashMap<String, Integer> slaveMap;

    /**
     * Constructor for SDMaterHeartbeats
     * @param list
     *          reference of slave list, used to track slaves
     */
    public SDMasterHeartbeats(ArrayList<SDSlave> list){
        responderList = new HashMap<Integer, Boolean>();
        slaveMap = new HashMap<String, Integer>();

        synchronized (list){
            slaveList = list;
            for(int i = 0; i < list.size(); i++){
                SDSlave slave = list.get(i);
                slaveMap.put(slave.getAddress() + slave.getPortString(), i);
            }
        }
    }

    public void run() {
        query();
        Timer timer = new Timer();
        timer.schedule(new SDMasterHeartbeats(slaveList), 1000, 2000);
        try {
            startListening(System.currentTimeMillis());
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }
        maintain();
    }



    private void query(){
        for (SDSlave node : slaveList){
            node.out.write("Alive?\n");
            node.out.flush();
        }
    }

    /**
     * create a UDP listener and receive heart beats replies.
     * @param currentTime
     *          heart beats triggering time
     * @throws IOException
     *          throws when UDP socket receive data wrongly.
     */
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
                    String receiveBuf = new String(packet.getData(), 0, packet.getLength());
                    String key = packet.getAddress().getHostAddress() + receiveBuf;
                    if(slaveMap.containsKey(key)){
                        responderList.put(slaveMap.get(key), true);
                    }else{
                        SDUtil.fatalError("Wrong slave key!!!!");
                    }
                } catch (SocketTimeoutException e){
                    System.err.println("UDP socket time out");
                }
            }
            listener.close();
        }
    }

    /**
     * get time in million seconds from 1-1-1970
     * @return
     *      current time in million seconds
     */
    private long getCurrentTimeInMillionSeconds(){
         Date currentData = new Date();
         return currentData.getTime();
    }

    private void maintain(){
        ArrayList<String> shutDown = new ArrayList<String>();
        for (String addr : slaveMap.keySet()){
            if (!responderList.containsKey(slaveMap.get(addr))){
                shutDown.add(addr);
            }
        }
        //print
        for (String addr : shutDown){
            System.out.println(slaveMap.get(addr));
            slaveMap.remove(addr);
        }
    }
}
