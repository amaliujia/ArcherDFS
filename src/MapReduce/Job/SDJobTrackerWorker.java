package MapReduce.Job;

import MapReduce.Master.SDJobTracker;

/**
 * Created by amaliujia on 15-1-9.
 */
public class SDJobTrackerWorker implements Runnable {

    private SDJobTracker jobTracker;

    private int jobID;

    public SDJobTrackerWorker(SDJobTracker jobTracker, int jobID){
        this.jobID = jobID;
        this.jobTracker = jobTracker;
    }

    @Override
    public void run() {
        try {
            jobTracker.startJob(jobID);
        } catch (Exception e) {
            jobTracker.startJobFailed(jobID);
        }
    }
}
