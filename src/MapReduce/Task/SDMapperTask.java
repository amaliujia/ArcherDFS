package MapReduce.Task;

import MapReduce.IO.SDFileBlock;
import MapReduce.Slave.SDTaskTrackerInfo;
import MapReduce.Util.SDMapReduceConstants;

import java.io.File;

/**
 * Created by amaliujia on 15-1-5.
 */
public class SDMapperTask extends SDTask {

    private SDFileBlock fileBlock;

    private int taskId;

    private int jobId;

    private SDTaskType type;

    private SDTaskStatus status;

    private int attemptCount;

    private String taskTrackerName;

    private SDTaskTrackerInfo taskTrackerInfo;

    private String mrClassName;

    private String outputDir;

    public SDMapperTask(int jobID, SDFileBlock fileBlock){
        super(jobID, SDTaskType.MAPPER);
        this.fileBlock = fileBlock;
    }


    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public SDTaskType getType() {
        return type;
    }

    public void setType(SDTaskType type) {
        this.type = type;
    }

    public SDTaskStatus getStatus() {
        return status;
    }

    public void setStatus(SDTaskStatus status) {
        this.status = status;
    }

    public int getAttemptCount(){
        return attemptCount;
    }

    public void increaseAttemptCount(){
        attemptCount++;
    }

    public void setAttemptCount(int count){
        attemptCount = count;
    }

    public String getTaskTrackerName() {
        return taskTrackerName;
    }

    public void setTaskTrackerName(String name){
        taskTrackerName = name;
    }

    public void setTaskTrackerName(SDTaskTrackerInfo taskTracker) {
        taskTracker.addTask(this);
        this.taskTrackerName = taskTracker.toString();
    }

    public String getMRClassName() {
        return mrClassName;
    }

    public void setMRClassName(String className) {
        this.mrClassName = className;
    }

    public String getTaskFolderName(){
        return SDMapReduceConstants.TASKS_FILE_URI + SDMapReduceConstants.FILE_SEPARATOR +
                    SDMapReduceConstants.TASK_FOLDER_PREFIX + taskId;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public void createTaskFolder(){
        File folder = new File(outputDir + SDMapReduceConstants.FILE_SEPARATOR + getTaskFolderName());
        if(folder.exists()){
            folder.delete();
        }
        folder.mkdirs();
    }

    public void deleteTaskFolder(){
        File folder = new File(outputDir + SDMapReduceConstants.FILE_SEPARATOR + getTaskFolderName());
        if(folder.exists()){
            folder.delete();
        }
    }
}

