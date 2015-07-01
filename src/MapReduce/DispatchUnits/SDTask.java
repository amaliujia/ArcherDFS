package MapReduce.DispatchUnits;

import MapReduce.TaskTracker.SDRemoteTaskObject;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author amaliujia
 */
public abstract class SDTask implements Serializable, Comparable<SDTask> {
    private static AtomicInteger maxId = new AtomicInteger(0);

    // Current mapreduce job id.
    protected int jobID;

    // Current mapper/reducer task id.
    // This task is belonging to job whose id is listed above.
    protected int taskID;

    // Mapper task or reducer task.
    protected SDTaskType taskType;

    // Compiled class name
    protected String mrClassName;

    // Bytes of compiled class.
    protected byte[] mrClass;

    // SDTask status.
    protected SDTaskStatus taskStatus;

    // RemoteTaskTracker
    protected SDRemoteTaskObject taskTracker;

    // Fault tolerance. Retry count is a num used for indicating how many time
    // task can be retry after failure.
    protected int retryCount = 3;

    public int compareTo(SDTask o) {
        if(this.taskID < o.getTaskID()){
            return -1;
        }else if(this.taskID > o.getTaskID()){
            return 1;
        }else{
            return 0;
        }
    }

    public void setJobID(int id){
        this.jobID = id;
    }

    public int getJobID(){
        return this.jobID;
    }

    public SDTask(){
        taskID = maxId.getAndIncrement();
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

    public void setMrClass(byte[] bytes){
        mrClass = bytes;
    }

    public byte[] getMrClass() {
        return mrClass;
    }

    public String getMrClassName() {
        return mrClassName;
    }

    public int getRetryCount(){
        return retryCount;
    }

    public void increaseRetryCount(){
        retryCount++;
    }

    public void setRetryCount(int count){
        retryCount = count;
    }

    public SDRemoteTaskObject getTaskTracker(){
        return taskTracker;
    }
}
