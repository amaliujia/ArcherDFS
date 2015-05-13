package MapReduce.DispatchUnits;

import MapReduce.MapReduceIO.SDFileSegment;

/**
 * @author amaliujia
 */
public class SDMapperTask extends SDTask{
    private int jobID;
    private SDFileSegment segment;

    public SDMapperTask(int id, SDFileSegment s){
        jobID = id;
        s = segment;
    }
}
