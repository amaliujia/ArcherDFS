package MapReduce.Master;

import MapReduce.Slave.SDTaskTrackerInfo;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by amaliujia on 15-1-4.
 */
public class SDJobTracker {

    private ConcurrentHashMap<String, SDTaskTrackerInfo> taskTrackerInfos;

    private ConcurrentHashMap<Integer, SDJobInfo> jobInfos;



    void start(){

    }
}
