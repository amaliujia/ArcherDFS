package MapReduce.Task;

import MapReduce.IO.SDFileBlock;

/**
 * Created by amaliujia on 15-1-5.
 */
public class SDReducerTask extends SDTask {

    private SDFileBlock inputFileBlock;

    public SDReducerTask(int jobID){
        super(jobID, SDTaskType.REDUCER);
    }
}
