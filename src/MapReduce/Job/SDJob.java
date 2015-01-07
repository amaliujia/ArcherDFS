package MapReduce.Job;

/**
 * Created by amaliujia on 15-1-7.
 */
public class SDJob {

    private String registryHost;

    private int registryPort;

    public SDJob(String registryHost, int registryPort){
        this.registryHost = registryHost;
        this.registryPort = registryPort;
    }

    public void run(SDJobConfig config, Class<?> mapreduceClass){
       //TODO how to start run a map reduce program.
    }

}
