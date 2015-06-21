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
    private int numOutputShards;

    //TODO:: set shards num here.
    public SDMapperTask(int id, SDFileSegment s){
        super();
        jobID = id;
        s = segment;
    }

    public void setOutputDir(String dir){
        outputDir = dir;
    }

    public SDFileSegment getSegment(){
        return segment;
    }

    public String getOutputDir(){
        return outputDir;
    }

    public int getNumOutputShards(){
        return numOutputShards;
    }
}

