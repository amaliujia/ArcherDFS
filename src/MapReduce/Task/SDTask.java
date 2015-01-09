package MapReduce.Task;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by amaliujia on 15-1-4.
 */
public class SDTask implements Serializable, Comparator<SDTask> {

    private SDTaskStatus status;

    private int id;

    private SDTaskType taskType;

    public SDTask(int jobid, SDTaskType type){
        id = jobid;
        taskType = type;
    }

    public int getTaskId() {
        return id;
    }

    @Override
    public int compare(SDTask o1, SDTask o2) {
        return 0;
    }

    public SDTaskStatus getStatus(){
        return status;
    }
}
