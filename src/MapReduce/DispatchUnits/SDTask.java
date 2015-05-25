package MapReduce.DispatchUnits;

import MapReduce.TaskTracker.SDRemoteTaskObject;

import java.io.Serializable;

/**
 * @author amaliujia
 */
public abstract class SDTask implements Serializable, Comparable<SDTask> {
    protected int taskID;
    protected SDTaskType taskType;
    protected String mrClassName;
    protected byte[] mrClass;
    protected SDTaskStatus taskStatus;
    protected SDRemoteTaskObject taskTracker;

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

    public void setTaskStatus(SDTaskStatus status){
        taskStatus = status;
    }

    public void setTaskTracker(SDRemoteTaskObject o){
        taskTracker = o;
    }

    public SDTaskStatus getTaskStatus(){
        return taskStatus;
    }

    public void setMrClassName(String className){
        this.mrClassName = className;
    }

    public void setMrClass(String classpath){

    }

}
