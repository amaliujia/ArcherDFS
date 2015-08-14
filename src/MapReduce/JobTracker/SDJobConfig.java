package MapReduce.JobTracker;

import java.io.Serializable;

/**
 * @author amaliujia
 */
public class SDJobConfig implements Serializable{
    public String jobName; //name of job.
    public String className;   // class of job.
    public String inputFile;   // inputfile, which should be stored into DFS already.
    public String outputFilePrefix;  // outputfile, which should be stored into local fs.
    public int numMapper; // how many machines run mapper.

    // How many machines run reducer. This num is actual the output shards for MapReduce job.
    public int numReducer;

    public Class<?> mapreduceclass;

    public SDJobConfig(String jobn, String cn, String ifile, String ofile, int nMapper, int nReduce,
                       Class<?> cl){
        jobName = jobn;
        className = cn;
        inputFile = ifile;
        outputFilePrefix = ofile;
        numMapper = nMapper;
        numReducer = nReduce;
        mapreduceclass = cl;
    }

    public String getJobName(){
        return jobName;
    }

    public String getClassName(){
        return className;
    }

    public String getInputFile(){
        return inputFile;
    }

    public String getOutputFile(){
        return outputFilePrefix;
    }

    public int getNumMapper(){
        return numMapper;
    }

    public int getNumReducer() {
        return numReducer;
    }
}
