package MapReduce.TaskTracker;

import MapReduce.Abstraction.SDMapReduce;
import MapReduce.Abstraction.SDOutputCollector;
import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.MapReduceIO.SDDFSDataGather;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * @author amaliujia
 */
public class SDTaskExecuteMapperWorker extends SDTaskExecuteWorker {

    private SDTaskTracker taskTracker;

    private SDMapperTask task;

    public SDTaskExecuteMapperWorker(SDTaskTracker taskTracker, SDMapperTask mapperTask){
        this.taskTracker = taskTracker;
        this.task = mapperTask;
    }

    private SDOutputCollector executeMapper(SDDFSDataGather gather) throws IllegalAccessException, InstantiationException {
        SDClassLoader classLoader = new SDClassLoader();
        Class<?> mapClass =  classLoader.findClass(task.getMrClassName(), task.getMrClass());
        SDMapReduce mapReduce = null;

        mapReduce = (SDMapReduce) mapClass.newInstance();

        String s = null;

        SDOutputCollector collector = new SDOutputCollector();
        while ((s = gather.readLine()) != null){
            mapReduce.map(s, collector);
        }

        return collector;
    }

    public void run() {
        // Read data from DFS into memory. Here assume data can fit into memory.
        SDDFSDataGather gather = new SDDFSDataGather(task.getSegment());
        try {
            gather.get();
            SDOutputCollector collector = executeMapper(gather);
            saveAsLocalFile(collector);
        } catch (Exception e) {
            //notify task tracker, this one fail.
            taskTracker.mapperTaskFail(task);
        }
        taskTracker.mapreduceTaskSucceed(task);
    }

    private void saveAsLocalFile(SDOutputCollector collector) throws IOException {
        String outputDir = task.getOutputDir() + "/" + task.getTaskID() +  "/";
        File[] mapperShards = new File[task.getNumOutputShards()];
        BufferedWriter[] writes = new BufferedWriter[task.getNumOutputShards()];

        for(int i = 0; i < mapperShards.length; i++){
            mapperShards[i] = new File(outputDir + "sharding-" + i);
            mapperShards[i].createNewFile();
            writes[i] = new BufferedWriter(new FileWriter(mapperShards[i]));
        }

        // TODO:: here should be provide sharding function interface.
        // TODO:: For now, it is ok to use hashcode mode #sharding.

        TreeMap<String, List<String>> sortedMap = collector.sortedMap();
        Iterator<String> iterator = sortedMap.keySet().iterator();
        String key;
        while (iterator.hasNext()){
            key = iterator.next();
            int hash = key.hashCode();
            int slot = hash % task.getNumOutputShards();
            List<String> values = sortedMap.get(key);
            for(String value : values){
                writes[slot].write(value + "\n");
            }
        }

        for(int i = 0; i < writes.length; i++){
            writes[i].flush();
            writes[i].close();
        }
    }
}
