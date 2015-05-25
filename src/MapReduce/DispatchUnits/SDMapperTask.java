package MapReduce.DispatchUnits;

import MapReduce.MapReduceIO.SDFileSegment;
import MapReduce.TaskTracker.SDRemoteTaskObject;

/**
 * @author amaliujia
 */
public class SDMapperTask extends SDTask{
    private int jobID;
    private SDFileSegment segment;
    private String outputDir;

    public SDMapperTask(int id, SDFileSegment s){
        jobID = id;
        s = segment;
    }

    public SDRemoteTaskObject getTaskTracker(){
        return taskTracker;
    }

    public void setOutputDir(String dir){
        outputDir = dir;
    }

    public SDFileSegment getSegment(){
        return segment;
    }
}
