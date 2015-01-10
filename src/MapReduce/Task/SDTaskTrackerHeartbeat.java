package MapReduce.Task;

import MapReduce.Slave.SDTaskTracker;

/**
 * Created by amaliujia on 15-1-9.
 */
public class SDTaskTrackerHeartbeat implements Runnable {
    private SDTaskTracker taskTracker;

    public SDTaskTrackerHeartbeat(SDTaskTracker taskTracker){
        this.taskTracker = taskTracker;
    }

    @Override
    public void run() {
        taskTracker.heartbeat();
    }
}
