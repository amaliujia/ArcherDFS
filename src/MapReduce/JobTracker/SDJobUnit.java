package MapReduce.JobTracker;

import MapReduce.DispatchUnits.SDJobStatus;
import MapReduce.DispatchUnits.SDTaskType;

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

    private SDJobStatus jobStatus;

    public SDJobUnit(SDJobConfig config){
        unitID = maxId.getAndIncrement();
        jobConfig = config;
        jobStatus = SDJobStatus.PENDING;
    }

    public SDJobConfig getJobConfig(){
        return jobConfig;
    }

    public int getID(){
        return  unitID;
    }

    public void setJobStatus(SDJobStatus s){
        jobStatus = s;
    }

    public SDJobStatus getJobStatus(){
        return jobStatus;
    }
}
