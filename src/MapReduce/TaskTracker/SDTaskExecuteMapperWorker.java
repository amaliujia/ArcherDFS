package MapReduce.TaskTracker;

import MapReduce.Abstraction.SDMapReduce;
import MapReduce.Abstraction.SDOutputCollector;
import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.MapReduceIO.SDDFSDataGather;

import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.NotBoundException;

/**
 * @author amaliujia
 */
public class SDTaskExecuteMapperWorker implements Runnable {

    private SDTaskTracker taskTracker;

    private SDMapperTask task;

    public SDTaskExecuteMapperWorker(SDTaskTracker taskTracker, SDMapperTask mapperTask){
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
        SDMapReduce mapReduce = null;

        try {
            mapReduce = (SDMapReduce) mapClass.newInstance();
        } catch (InstantiationException e) {
            //notify task tracker, this one fail.
        } catch (IllegalAccessException e) {
            //notify task tracker, this one fail.
        }

        if(mapReduce == null){
            //notify task tracker, this one fail.
        }

        String s = null;

        SDOutputCollector collector = new SDOutputCollector();
        while ((s = gather.readLine()) != null){
            mapReduce.map(s, collector);
        }

        saveAsFile(collector);
    }

    private void saveAsFile(SDOutputCollector collector){
        //TODO: write map result into files. Implicitly shuffle here.
    }
}
