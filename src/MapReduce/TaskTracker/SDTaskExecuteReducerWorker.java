package MapReduce.TaskTracker;

import MapReduce.Abstraction.SDMapper;
import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDReducerTask;

import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by amaliujia on 15-6-21.
 */
public class SDTaskExecuteReducerWorker implements Runnable {

    private SDReducerTask reducerTask;
    private SDTaskTracker taskTracker;
    private PriorityQueue<SDMapperTask> mapperTasks;

    public SDTaskExecuteReducerWorker(SDReducerTask task, SDTaskTracker taskTracker){
        this.reducerTask = task;
        this.taskTracker = taskTracker;
    }

    public void addMapperTask(SDMapperTask task){
        mapperTasks.offer(task);
    }

    public void run() {

    }
}
