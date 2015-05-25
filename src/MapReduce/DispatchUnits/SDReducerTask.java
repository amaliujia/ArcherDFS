package MapReduce.DispatchUnits;

import MapReduce.TaskTracker.SDRemoteTaskObject;

/**
 * @author amaliujia
 */
public class SDReducerTask extends SDTask {
    private int jobID;


    public SDReducerTask(int id){
        jobID = id;
    }


}
