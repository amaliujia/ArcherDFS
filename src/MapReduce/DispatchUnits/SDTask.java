package MapReduce.DispatchUnits;

import java.io.Serializable;

/**
 * @author amaliujia
 */
public abstract class SDTask implements Serializable, Comparable<SDTask> {
    protected int taskID;
    protected SDTaskType taskType;

    public int compareTo(SDTask o) {
        if(this.taskID < o.getTaskID()){
            return -1;
        }else if(this.taskID > o.getTaskID()){
            return 1;
        }else{
            return 0;
        }
    }

    public int getTaskID(){
        return this.taskID;
    }

}
