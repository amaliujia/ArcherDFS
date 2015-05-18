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
    private long timestamp;
    private final long period = 1000000;

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

    public int getHostport(){
        return hostport;
    }

    public void setTimestamp(){
        timestamp = System.currentTimeMillis();
    }


    public int getMapperTaskNumber(){
        return mapperTaskNumber;
    }

    public int getReduceTaskNumber(){
        return reduceTaskNumber;
    }

    public boolean isValid(){
        if((System.currentTimeMillis() - this.timestamp) < period){
            return true;
        }
        return false;
    }
}
