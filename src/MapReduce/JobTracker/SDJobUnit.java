package MapReduce.JobTracker;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by amaliujia on 15-5-11.
 */
public class SDJobUnit {
    public static AtomicInteger maxId = new AtomicInteger(0);

    private int unitID;

    public SDJobUnit(SDJobConfig config){
        unitID = maxId.getAndIncrement();
    }


    public int getID(){
        return  unitID;
    }
}
