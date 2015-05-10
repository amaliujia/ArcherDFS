package MapReduce.Abstraction;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by amaliujia on 15-5-9.
 */
public class SDMapReduce implements SDMapper, SDReducer, Serializable {
    public void map(Object key, Object value, SDOutputCollector output) {

    }

    public void reduce(Object key, Iterator values, SDOutputCollector output) {

    }

    public void run(String[] args){
//        JCommander commander = new JCommander(this, args);
//        commander.setProgramName("simplemr-examples");
//        if(needHelp()){
//            commander.usage();
//        } else {
//            JobConfig jobConfig = getJobConfig();
//            Job job = new Job(registryHost, registryPort);
//            job.run(jobConfig, this.getClass());
//        }
    }
}
