package MapReduce.JobTracker;

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by amaliujia on 15-5-11.
 */
public class SDJobUnit {
    public static AtomicInteger maxId = new AtomicInteger(0);

    private int unitID;

    private SDJobConfig jobConfig;

    public SDJobUnit(SDJobConfig config){
        unitID = maxId.getAndIncrement();
        jobConfig = config;
    }



    public SDJobConfig getJobConfig(){
        return jobConfig;
    }

    public int getID(){
        return  unitID;
    }
}
