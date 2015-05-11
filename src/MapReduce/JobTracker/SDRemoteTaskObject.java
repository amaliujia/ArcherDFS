package MapReduce.JobTracker;

import MapReduce.DispatchUnits.SDTask;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author amaliujia
 */
public class SDRemoteTaskObject implements Serializable{
    private String hostname;
    private int hostport;
    private int mapperTaskNumber;
    private int reduceTaskNumber;
    private Set<SDTask> tasks;

    public SDRemoteTaskObject(String name, int port){
        this.hostname = name;
        this.hostport = port;
        this.tasks = new TreeSet<SDTask>();
        this.mapperTaskNumber = 0;
        this.reduceTaskNumber = 0;
    }


}
