package Heartbeats;

import Master.SDSlave;

import java.util.ArrayList;

/**
 * Created by amaliujia on 14-12-20.
 */
public class SDMaterHeartbeats implements Runnable{

    private ArrayList<SDSlave> slaveList;

    public SDMaterHeartbeats(ArrayList<SDSlave> list){
        slaveList = list;
    }

    public void run() {

    }
}
