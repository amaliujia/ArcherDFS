package MapReduce.JobTracker;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author amaliujia
 */
public class SDJobConfig {
    public String jobName; //name of job.
    public String className;   // class of job.
    public String inputFile;   // inputfile, which should be stored into DFS already.
    public String outputFile;  // outputfile, which should be stored into local fs.
    public int numMapper; // how many machines run mapper.
    public int numReduce; // how many machines run reducer.
    public Class<?> mapreduceclass;

    public SDJobConfig(String jobn, String cn, String ifile, String ofile, int nMapper, int nReduce,
                       Class<?> cl){
        jobName = jobn;
        className = cn;
        inputFile = ifile;
        outputFile = ofile;
        numMapper = nMapper;
        numReduce = nReduce;
        mapreduceclass = cl;
    }
}
