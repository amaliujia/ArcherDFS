package MapReduce.Task;

import MapReduce.IO.SDFileBlock;

/**
 * Created by amaliujia on 15-1-5.
 */
public class SDMapperTask extends SDTask {

    private SDFileBlock fileBlock;

    public SDMapperTask(int jobID, SDFileBlock fileBlock){
        super(jobID, SDTaskType.MAPPER);
        this.fileBlock = fileBlock;
    }
}

