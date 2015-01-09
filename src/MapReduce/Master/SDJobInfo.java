package MapReduce.Master;

import MapReduce.Job.SDJobConfig;
import MapReduce.Job.SDJobStatus;
import MapReduce.Task.SDMapperTask;
import MapReduce.Task.SDReducerTask;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by amaliujia on 15-1-5.
 */
public class SDJobInfo {

    private static AtomicInteger maxId = new AtomicInteger(0);

    private int id;

    private SDJobConfig config;

    private SDJobStatus status;

    private Map<Integer, SDMapperTask> mapperTasks;

    private Map<Integer, SDReducerTask> reducerTasks;

    public SDJobInfo(SDJobConfig jobConfig){
        this.id = maxId.getAndIncrement();
        this.config = jobConfig;
        this.status = SDJobStatus.INITIALIZING;
        this.mapperTasks = new HashMap<Integer, SDMapperTask>();
        this.reducerTasks = new HashMap<Integer, SDReducerTask>();
    }

    public void setJobStatus(SDJobStatus status){
        this.status = status;
    }
}
