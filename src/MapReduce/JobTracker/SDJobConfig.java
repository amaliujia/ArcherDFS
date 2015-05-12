package MapReduce.JobTracker;

/**
 * @author amaliujia
 */
public class SDJobConfig {
    public String jobName; //name of job.
    public String className;   // class of job.
    public String inputFile;   // inputfile, which should be stored into DFS already.
    public String outputFile;  // outputfile, which should be stored into local fs.
    public int numMapper; // how many machines run mapper.
    public int numReducer; // how many machines run reducer.
    public Class<?> mapreduceclass;

    public SDJobConfig(String jobn, String cn, String ifile, String ofile, int nMapper, int nReduce,
                       Class<?> cl){
        jobName = jobn;
        className = cn;
        inputFile = ifile;
        outputFile = ofile;
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
        return outputFile;
    }

    public int getNumMapper(){
        return numMapper;
    }

    public int getNumReducer() {
        return numReducer;
    }
}
