package MapReduce.Common;

import FileSystem.Util.SDDFSConstants;
import MapReduce.Job.SDJobConfig;

/**
 * Created by amaliujia on 15-1-7.
 */
public abstract class SDAbstractMapReduce implements SDMapReduce {
    private static final int DEFAULT_ATTEMPT_COUNT = 3;

    private String jobName;

    private String className;

    private String inputFile;

    private String outputFile;

    private int outputFileReplica = SDDFSConstants.DEFAULT_REPLICA_NUMBER;

    private int outputFileBlockSize = SDDFSConstants.DEFAULT_LINE_COUNT;

    private int mapperAmount = 0;

    private int reducerAmount = 0;

    private int maxAttemptCount = DEFAULT_ATTEMPT_COUNT;

    public void run(String[] args){
            SDJobConfig jobConfig = getJobConfig();
//            SDJob job = new SDJob(registryHost, registryPort);
//            job.run(jobConfig, this.getClass());

    }

    public SDJobConfig getJobConfig(){
        SDJobConfig jobConfig = new SDJobConfig();

        //TODO: configure job environment.
//        if(files.size() > 0){
//            jobConfig.setInputFile(files.get(0));
//        }
//        if(files.size() > 1){
//            jobConfig.setOutputFile(files.get(1));
//        }
//        jobConfig.setJobName(jobName);
//        jobConfig.setMapperAmount(mapperAmount);
//        jobConfig.setReducerAmount(reducerAmount);
//        jobConfig.setMaxAttemptCount(maxAttemptCount);
//        jobConfig.setOutputFileReplica(replicas);
//        jobConfig.setOutputFileBlockSize(lineCount);
//        jobConfig.setClassName(this.getClass().getName());
//        jobConfig.validate();
        return jobConfig;
    }
}
