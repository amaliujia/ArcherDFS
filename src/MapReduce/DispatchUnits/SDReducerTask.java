package MapReduce.DispatchUnits;

import MapReduce.TaskTracker.SDRemoteTaskObject;

/**
 * @author amaliujia
 */
public class SDReducerTask extends SDTask {
    private int jobID;

    private SDTaskStatus taskStatus;
    private SDRemoteTaskObject taskTracker;


    public SDReducerTask(int id){
        jobID = id;
    }

    public void setTaskStatus(SDTaskStatus status){
        taskStatus = status;
    }

    public void setTaskTracker(SDRemoteTaskObject o){
        taskTracker = o;
    }
}
