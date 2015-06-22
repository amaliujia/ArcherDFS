package MapReduce.JobTracker;

import MapReduce.Abstraction.SDMapper;
import MapReduce.Abstraction.SDReducer;
import MapReduce.DispatchUnits.SDJobStatus;
import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDReducerTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by amaliujia on 15-5-11.
 */
public class SDJobUnit {
    public static AtomicInteger maxId = new AtomicInteger(0);

    private int unitID;

    private SDJobConfig jobConfig;

    private SDJobStatus jobStatus;

    private Map<Integer, SDMapperTask> mapperTaskMap;

    private Map<Integer, SDReducerTask> reducerTaskMap;

    public SDJobUnit(SDJobConfig config){
        unitID = maxId.getAndIncrement();
        jobConfig = config;
        jobStatus = SDJobStatus.PENDING;

        mapperTaskMap = new TreeMap<Integer, SDMapperTask>();
        reducerTaskMap = new TreeMap<Integer, SDReducerTask>();
    }

    public SDJobConfig getJobConfig(){
        return jobConfig;
    }

    public int getID(){
        return unitID;
    }

    public void setJobStatus(SDJobStatus s){
        jobStatus = s;
    }

    public SDJobStatus getJobStatus(){
        return jobStatus;
    }

    public void addMapperTask(SDMapperTask task){
        mapperTaskMap.put(task.getTaskID(), task);
    }

    public Map<Integer, SDMapperTask> getMapperTaskMap(){
        return mapperTaskMap;
    }

    public SDMapperTask getMapperTask(int i){
        return mapperTaskMap.get(i);
    }

    public void addReducerTask(SDReducerTask task){
        reducerTaskMap.put(task.getTaskID(), task);
    }

    public List<SDReducerTask> getReducerTasksInArray(){
        return new ArrayList<SDReducerTask>(reducerTaskMap.values());
    }

    public List<SDMapperTask> getMapperTasksInArray(){
        return new ArrayList<SDMapperTask>(mapperTaskMap.values());
    }


    public Map<Integer, SDReducerTask> getReducerTaskMap(){
        return reducerTaskMap;
    }
}
