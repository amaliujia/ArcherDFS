package MapReduce.Job;

import FileSystem.Util.SDDFSConstants;

import java.io.Serializable;

/**
 * Created by amaliujia on 15-1-7.
 */
public class JobConfig implements Serializable {

    private String jobName;

    private String className;

    private String inputFile;

    private String outputFile;

    private int outputFileReplica = SDDFSConstants.DEFAULT_REPLICA_NUMBER;

    private int outputFileBlockSize = SDDFSConstants.DEFAULT_LINE_COUNT;

    private int mapperAmount = 0;

    private int reducerAmount = 0;

    private int maxAttemptCount = SDDFSConstants.DEFAULT_ATTEMPT_COUNT;


    public int getMaxAttemptCount(){
        return maxAttemptCount;
    }

    public void setMaxAttemptCount(int maxAttemptCount){
        this.maxAttemptCount = maxAttemptCount;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public int getMapperAmount() {
        return mapperAmount;
    }

    public void setMapperAmount(int mapperAmount) {
        this.mapperAmount = mapperAmount;
    }

    public int getReducerAmount() {
        return reducerAmount;
    }

    public void setReducerAmount(int reducerAmount) {
        this.reducerAmount = reducerAmount;
    }

    public int getOutputFileReplica() {
        return outputFileReplica;
    }

    public void setOutputFileReplica(int outputFileReplica) {
        this.outputFileReplica = outputFileReplica;
    }

    public int getOutputFileBlockSize() {
        return outputFileBlockSize;
    }
}
