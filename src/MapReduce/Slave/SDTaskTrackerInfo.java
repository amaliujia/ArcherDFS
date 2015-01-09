package MapReduce.Slave;

import MapReduce.Task.SDMapperTask;
import MapReduce.Task.SDReducerTask;
import MapReduce.Task.SDTask;
import MapReduce.Task.SDTaskStatus;
import MapReduce.Util.SDMapReduceConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public SDTask[] getTasks(){
        SDTask[] taskArray = new SDTask[tasks.size()];
        tasks.toArray(taskArray);
        return taskArray;
    }

    public List<SDTask> getPendingTasks(){
        List<SDTask> pendingTasks = new ArrayList<SDTask>();
        for(SDTask task : tasks){
            if(task.getStatus() == SDTaskStatus.PENDING){
                pendingTasks.add(task);
            }
        }
        return pendingTasks;
    }

    public List<SDMapperTask> getPendingMapperTask(){
        List<SDMapperTask> pendingMapperTasks = new ArrayList<SDMapperTask>();
        for(SDTask task : tasks){
            if(task instanceof SDMapperTask && task.getStatus() == SDTaskStatus.PENDING){
                pendingMapperTasks.add((SDMapperTask)task);
            }
        }
        return pendingMapperTasks;
    }

    public List<SDReducerTask> getPendingReducerTask(){
        List<SDReducerTask> pendingReducerTasks = new ArrayList<SDReducerTask>();
        for(SDTask task : tasks){
            if(task instanceof SDReducerTask && task.getStatus() == SDTaskStatus.PENDING){
                pendingReducerTasks.add((SDReducerTask)task);
            }
        }
        return pendingReducerTasks;
    }

    public void addTask(SDTask task){
        tasks.add(task);
    }


    public void removeTask(SDTask task){
        tasks.remove(task);
    }

    public String getHost() {
        return host;
    }

    public int getRegistryPort(){
        return registryPort;
    }

    public int getFileServerPort() {
        return fileServerPort;
    }

    public int getMapperTaskNumber() {
        return mapperTaskNumber;
    }

    public int getReducerTaskNumber() {
        return reduceTaskNumber;
    }

    public void increaseMapperTaskNumber(){
        mapperTaskNumber++;
    }

    public void increaseReducerTaskNumber(){
        reduceTaskNumber++;
    }

    public void decreaseMapperTaskNumber(){
        mapperTaskNumber--;
    }

    public void decreaseReducerTaskNumber(){
        reduceTaskNumber--;
    }

    public void setMapperTaskNumber(int number){
        mapperTaskNumber = number;
    }

    public void setReduceTaskNumber(int number){
        reduceTaskNumber = number;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    public boolean isValid(){
        if(System.currentTimeMillis() - timestamp > invalidPeriod){
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object taskTrackerInfo){
        if(taskTrackerInfo instanceof SDTaskTrackerInfo){
            return (host.equals(((SDTaskTrackerInfo) taskTrackerInfo).getHost())) &&
                    (fileServerPort == ((SDTaskTrackerInfo) taskTrackerInfo).getFileServerPort());
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return host + ":" + getFileServerPort();
    }

    @Override
    public int hashCode(){
        return toString().hashCode();
    }
}
