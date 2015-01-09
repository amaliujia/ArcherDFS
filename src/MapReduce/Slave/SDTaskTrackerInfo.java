package MapReduce.Slave;

import MapReduce.Task.SDTask;
import MapReduce.Util.SDMapReduceConstants;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by amaliujia on 15-1-5.
 */
public class SDTaskTrackerInfo implements Serializable {

    private String host;

    private int registryPort;

    private int fileServerPort;

    private int mapperTaskNumber;

    private int reduceTaskNumber;

    private long timestamp;

    private long invalidPeriod;

    private Set<SDTask> tasks;

    public SDTaskTrackerInfo(String host, int registryPort, int fileServerPort, long invalidPeriod){
        this.host = host;
        this.registryPort = registryPort;
        this.fileServerPort = fileServerPort;
        this.mapperTaskNumber = 0;
        this.reduceTaskNumber = 0;
        this.timestamp = 0;
        this.invalidPeriod = invalidPeriod;
        this.tasks = new HashSet<SDTask>();
    }

    public SDTaskTrackerInfo(String host, int registryPort, int fileServerPort){
        this(host, registryPort, fileServerPort, SDMapReduceConstants.DEFAULT_HEARTBEAT_INVALID);
    }
}
