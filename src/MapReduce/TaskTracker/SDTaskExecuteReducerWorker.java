package MapReduce.TaskTracker;

import MapReduce.Abstraction.SDMapReduce;
import MapReduce.Abstraction.SDOutputCollector;
import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDReducerTask;
import Protocol.MapReduce.SDTaskService;

import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

/**
 * Created by amaliujia on 15-6-21.
 */
public class SDTaskExecuteReducerWorker implements Runnable {

    private SDReducerTask reducerTask;
    private SDTaskTracker taskTracker;
    private PriorityQueue<SDMapperTask> mapperTasks;
    private boolean run;
    private String reducerShard;

    private byte[] dataBuffer;
    private int count;

    public SDTaskExecuteReducerWorker(SDReducerTask task, SDTaskTracker taskTracker){
        this.reducerTask = task;
        this.taskTracker = taskTracker;
        run = false;
        reducerShard = reducerTask.getOutputFilePrefix() + "reducer-shard";
    }

    public void addMapperTask(SDMapperTask task){
        mapperTasks.offer(task);
    }

    public boolean ifRun(){
        if (run == false && mapperTasks.size() == reducerTask.getNumMappers()){
            return true;
        }
        return false;
    }

    public void run() {
        while (true){
            if (mapperTasks.size() == 0){
                taskTracker.reducerTaskFail(this.reducerTask);
                return;
            }

            for(int i = 0; i < mapperTasks.size(); i++) {
                SDMapperTask mapperTask = mapperTasks.poll();
                SDRemoteTaskObject taskObject = mapperTask.getTaskTracker();
                try {
                    ArrayList<String> mapperShards = new ArrayList<String>();
                    Registry registry = LocateRegistry.getRegistry(taskObject.getHostname(), taskObject.getHostport());
                    //if service name is ok?
                    SDTaskService service = (SDTaskService) registry.lookup(SDTaskService.class.getCanonicalName());
                    dataBuffer = service.getsShards(mapperTask.getOutputDir() + "sharding-" + reducerTask.getNumShards());
                    String localPath = reducerTask.getOutputFilePrefix() + "sharding-" + i;
                    mapperShards.add(localPath);
                    RandomAccessFile file = new RandomAccessFile(localPath, "w");
                    file.write(dataBuffer, 0, dataBuffer.length);
                    file.close();
                    shuffle(mapperShards);
                    reduce(reducerShard);
                } catch (Exception e) {
                    //TODO: More detail on these errors;
                    //Add to fail
                    taskTracker.reducerTaskFail(reducerTask);
                }
            }
        }
    }

    private void shuffle(List<String> files) throws IOException {
        TreeMap<String, String> inmemoryMap = new TreeMap<String, String>();
        for (String file : files) {
            BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
            String line = null;
            while ((line = reader.readLine()) != null){
                String[] words = line.split(" ");
                if(words.length == 0){
                    continue;
                } else if(words.length == 1){
                    if(!inmemoryMap.containsKey(words[0])){
                        inmemoryMap.put(words[0], "");
                    }
                } else if(words.length == 2){
                    if(!inmemoryMap.containsKey(words[0])){
                        inmemoryMap.put(words[0], words[1]);
                    }else{
                        String value = inmemoryMap.get(words[0]);
                        if (value.length() == 0){
                            value = words[1];
                        }else{
                            value += " " + words[1];
                        }
                        inmemoryMap.put(words[0], value);
                    }
                }
            }
        }

        //write shuffled data to file.
        Iterator<String> iter = inmemoryMap.keySet().iterator();
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(this.reducerShard)));
        String key = null;
        String value = null;
        while (iter.hasNext()){
            key = iter.next();
            value = inmemoryMap.get(key);
            if(value == ""){
                continue;
            }
            writer.write(key + " " + value);
            writer.newLine();
        }
        writer.flush();
        writer.close();
    }

    private void reduce(String reducerShard) throws IllegalAccessException, InstantiationException, IOException {
        SDClassLoader classLoader = new SDClassLoader();
        Class<?> mapClass =  classLoader.findClass(reducerTask.getMrClassName(), reducerTask.getMrClass());
        SDMapReduce mapReduce = null;

        mapReduce = (SDMapReduce) mapClass.newInstance();
        String curRow = null;
        BufferedReader reader = new BufferedReader(new FileReader(new File(reducerShard)));
        SDOutputCollector outputCollector = new SDOutputCollector();
        while ((curRow = reader.readLine()) != null){
            String[] tokens = curRow.split(" ");
            String key = tokens[0];
            List<String> a = Arrays.asList(tokens);
            a.remove(0);
            mapReduce.reduce(key, a.iterator(), outputCollector);
        }
        //TODO: save reduce output to DFS.
        saveAsLocalFile(outputCollector);
        taskTracker.reducerTaskSucceed(reducerTask);
    }

    private void saveAsLocalFile(SDOutputCollector collector) throws IOException {
        String outputDir = reducerTask.getOutputFilePrefix() + "/" + reducerTask.getTaskID() +  "/";
        File reducerShard = new File(outputDir);
        BufferedWriter write = new BufferedWriter(new FileWriter(reducerShard));


        TreeMap<String, List<String>> sortedMap = collector.sortedMap();
        Iterator<String> iterator = sortedMap.keySet().iterator();
        String key;
        while (iterator.hasNext()){
            key = iterator.next();
            List<String> values = sortedMap.get(key);
            write.write(values.get(0) + "\n");
        }
    }
}
