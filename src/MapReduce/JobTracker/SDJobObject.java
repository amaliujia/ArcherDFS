package MapReduce.JobTracker;

import MapReduce.DispatchUnits.SDTask;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by amaliujia on 15-5-9.
 */
public class SDJobObject implements Serializable{
    private String hostname;
    private int hostport;
    private int mapperTaskNumber;
    private int reduceTaskNumber;
    private Set<SDTask> tasks;

    public SDJobObject(String name, int port){
        this.hostname = name;
        this.hostport = port;
        this.tasks = new TreeSet<SDTask>();
        this.mapperTaskNumber = 0;
        this.reduceTaskNumber = 0;
    }


}
