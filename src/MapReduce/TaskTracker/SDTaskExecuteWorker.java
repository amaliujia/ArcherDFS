package MapReduce.TaskTracker;

import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.MapReduceIO.SDDFSDataGather;

import java.io.IOException;
import java.rmi.NotBoundException;

/**
 * @author amaliujia
 */
public class SDTaskExecuteWorker implements Runnable {

    private SDTaskTracker taskTracker;

    private SDMapperTask task;

    public SDTaskExecuteWorker(SDTaskTracker taskTracker, SDMapperTask mapperTask){
        this.taskTracker = taskTracker;
        this.task = mapperTask;
    }

    public void run() {
        // Read data from DFS into memory. Here assume data can fit into memory.
        SDDFSDataGather gather = new SDDFSDataGather(task.getSegment());
        try {
            gather.get();
        } catch (IOException e) {
            //notify task tracker, this one fail.
        } catch (NotBoundException e) {
            //notify task tracker, this one fail.
        }


        SDClassLoader classLoader = new SDClassLoader();
        Class<?> mapClass =  classLoader.findClass(task.getMrClassName(), task.getMrClass());


    }
}
