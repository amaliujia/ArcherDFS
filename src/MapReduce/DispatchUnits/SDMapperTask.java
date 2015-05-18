package MapReduce.DispatchUnits;

import MapReduce.MapReduceIO.SDFileSegment;
import MapReduce.TaskTracker.SDRemoteTaskObject;

/**
 * @author amaliujia
 */
public class SDMapperTask extends SDTask{
    private int jobID;
    private SDFileSegment segment;

    private SDTaskStatus taskStatus;
    private SDRemoteTaskObject taskTracker;


    public SDMapperTask(int id, SDFileSegment s){
        jobID = id;
        s = segment;
    }

    public void setTaskStatus(SDTaskStatus status){
        taskStatus = status;
    }

    public void setTaskTracker(SDRemoteTaskObject o){
        taskTracker = o;
    }

    public SDTaskStatus getTaskStatus(){
        return taskStatus;
    }

    public SDRemoteTaskObject getTaskTracker(){
        return taskTracker;
    }
}
