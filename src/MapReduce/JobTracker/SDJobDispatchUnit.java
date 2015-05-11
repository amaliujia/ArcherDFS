package MapReduce.JobTracker;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author amaliujia
 */
public class SDJobDispatchUnit implements Runnable {

    private SDJobUnit jobUnit;
    public SDJobDispatchUnit(SDJobUnit unit){
        this.jobUnit = unit;
    }

    public void run() {
        // start to initialize MapReduce job
    }
}
