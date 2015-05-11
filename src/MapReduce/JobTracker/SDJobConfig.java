package MapReduce.JobTracker;

/**
 * @author amaliujia
 */
public class SDJobConfig {
    private String jobName; //name of job.
    private String className;   // class of job.
    private String inputFile;   // inputfile, which should be stored into DFS already.
    private String outputFile;  // outputfile, which should be stored into local fs.
    private int numMapper; // how many machines run mapper.
    private int numReduce; // how many machines run reducer.

    public SDJobConfig(String jobn, String cn, String ifile, String ofile, int nMapper, int nReduce){
        jobName = jobn;
        className = cn;
        inputFile = ifile;
        outputFile = ofile;
        numMapper = nMapper;
        numReduce = nReduce;
    }
}
