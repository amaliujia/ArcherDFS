package MapReduce.TaskTracker;

import java.io.Serializable;

/**
 * @author amaliujia
 */
public class SDRemoteTaskObject implements Serializable{
    private String hostname;
    private int hostport;
    private int mapperTaskNumber;
    private int reduceTaskNumber;
    //private Set<SDTask> tasks;

    public SDRemoteTaskObject(String name, int port){
        this.hostname = name;
        this.hostport = port;
        //this.tasks = new TreeSet<SDTask>();
        this.mapperTaskNumber = 0;
        this.reduceTaskNumber = 0;
    }

    public void setMapperTaskNumber(int i){
        mapperTaskNumber = i;
    }

    public void setReduceTaskNumber(int i){
       reduceTaskNumber = i;
    }

    public String getHostname(){
        return hostname;
    }
}
