package MapReduce.Job;

import MapReduce.Task.SDMapperTask;
import MapReduce.Task.SDReducerTask;
import MapReduce.Task.SDTask;
import MapReduce.Task.SDTaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by amaliujia on 15-1-8.
 */
public class SDJobInfo {
    private static AtomicInteger maxID = new AtomicInteger(0);

    private int id;

    private SDJobConfig jobConfig;

    private SDJobStatus jobStatus;

    private Map<Integer, SDMapperTask> mapperTasks;

    private Map<Integer, SDReducerTask> reducerTasks;

    public SDJobInfo(SDJobConfig jobConfig){
        this.id = maxID.getAndDecrement();
        this.jobConfig = jobConfig;
        this.jobStatus = jobStatus.INITIALIZING;
        this.mapperTasks = new HashMap<Integer, SDMapperTask>();
        this.reducerTasks = new HashMap<Integer, SDReducerTask>();
    }

    public List<SDMapperTask> getMapperTasks(){
        return new ArrayList<SDMapperTask>(mapperTasks.values());
    }

    public List<SDReducerTask> getReducerTasks(){
        return new ArrayList<SDReducerTask>(reducerTasks.values());
    }

    public SDTask getTask(int taskId){
        SDTask task = mapperTasks.get(taskId);
        if(task == null){
            task = reducerTasks.get(taskId);
        }
        return task;
    }

    public SDJobStatus getStatus(){
        return jobStatus;
    }

    public void setJobStatus(SDJobStatus status){
        this.jobStatus = status;
    }

    public void addMapperTask(SDMapperTask mapperTask){
        mapperTasks.put(mapperTask.getTaskId(), mapperTask);
    }

    public void addReducerTask(SDReducerTask reducerTask){
        reducerTasks.put(reducerTask.getTaskId(), reducerTask);
    }

    public int getId(){
        return id;
    }

    public SDJobConfig getConfig(){
        return jobConfig;
    }

    private String describeJob(){
        StringBuffer sb = new StringBuffer();
        SDJobStatus status = checkJobStatus();
        sb.append("#" + id);
        sb.append("\t");
        sb.append(jobConfig.getJobName());
        sb.append("\t");
        sb.append(status.toString());
        sb.append("\t(");
        sb.append(describeMapperTasks());
        sb.append(" || ");
        sb.append(describeReducerTasks());
        sb.append(")");
        return sb.toString();
    }

    private String describeMapperTasks(){
        int pendingCount = 0;
        int successCount = 0;
        int failureCount = 0;
        for(SDMapperTask mapperTask : getMapperTasks()){
            if(mapperTask.getStatus() == SDTaskStatus.PENDING){
                pendingCount++;
            }
            if(mapperTask.getStatus() == SDTaskStatus.SUCCEED){
                successCount++;
            }
            if(mapperTask.getStatus() == SDTaskStatus.FAILED){
                failureCount++;
            }
        }
        return "mapper task: " + pendingCount + " pending, " + successCount +
                " succeeded, " + failureCount + " failed";
    }

    private String describeReducerTasks(){
        int pendingCount = 0;
        int successCount = 0;
        int failureCount = 0;
        for(SDReducerTask reducerTask : getReducerTasks()){
            if(reducerTask.getStatus() == SDTaskStatus.PENDING){
                pendingCount++;
            }
            if(reducerTask.getStatus() == SDTaskStatus.SUCCEED){
                successCount++;
            }
            if(reducerTask.getStatus() == SDTaskStatus.FAILED){
                failureCount++;
            }
        }
        return "reducer task: " + pendingCount + " pending, " + successCount +
                " succeeded, " + failureCount + " failed";
    }

    private SDJobStatus checkJobStatus(){
        boolean failure = false;
        boolean pending = false;
        if(jobStatus != SDJobStatus.PENDING){
            return jobStatus;
        }
        for(SDTask task : getMapperTasks()){
            if(task.getStatus() == SDTaskStatus.PENDING){
                pending = true;
            }
            if(task.getStatus() == SDTaskStatus.FAILED){
                failure = true;
                break;
            }
        }

        for(SDTask task : getReducerTasks()){
            if(task.getStatus() == SDTaskStatus.PENDING){
                pending = true;
            }
            if(task.getStatus() == SDTaskStatus.FAILED){
                failure = true;
                break;
            }
        }

        if(failure){
            setJobStatus(SDJobStatus.FAILED);
            return jobStatus;
        }
        if(pending){
            setJobStatus(SDJobStatus.PENDING);
            return jobStatus;
        }
        setJobStatus(SDJobStatus.SUCCEED);
        return jobStatus;
    }

    public String toString(){
        return describeJob();
    }


}