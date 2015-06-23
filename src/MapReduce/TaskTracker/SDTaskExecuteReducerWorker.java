package MapReduce.TaskTracker;

import MapReduce.Abstraction.SDMapReduce;
import MapReduce.Abstraction.SDMapper;
import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDReducerTask;
import Protocol.MapReduce.SDTaskService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by amaliujia on 15-6-21.
 */
public class SDTaskExecuteReducerWorker implements Runnable {

    private SDReducerTask reducerTask;
    private SDTaskTracker taskTracker;
    private PriorityQueue<SDMapperTask> mapperTasks;

    private byte[] dataBuffer;
    private int count;

    public SDTaskExecuteReducerWorker(SDReducerTask task, SDTaskTracker taskTracker){
        this.reducerTask = task;
        this.taskTracker = taskTracker;
    }

    public void addMapperTask(SDMapperTask task){
        mapperTasks.offer(task);
    }

    public void run() {
        while (true){
            if (mapperTasks.size() == 0){
                return;
            }

            SDMapperTask mapperTask = mapperTasks.poll();
            SDRemoteTaskObject taskObject = mapperTask.getTaskTracker();
            try {
                Registry registry = LocateRegistry.getRegistry(taskObject.getHostname(), taskObject.getHostport());
                //if service name is ok?
                SDTaskService service = (SDTaskService)registry.lookup(SDTaskService.class.getCanonicalName());
                dataBuffer = service.getsShards(mapperTask.getOutputDir() + "sharindg-" + reducerTask.getNumShards());
                reduce();
            } catch (Exception e) {
                //Add to fail
                taskTracker.reducerTaskFail(reducerTask);
            }
        }
    }

    public void reduce() throws IllegalAccessException, InstantiationException {

        SDClassLoader classLoader = new SDClassLoader();
        Class<?> mapClass =  classLoader.findClass(reducerTask.getMrClassName(), reducerTask.getMrClass());
        SDMapReduce mapReduce = null;

        mapReduce = (SDMapReduce) mapClass.newInstance();
        String curRow = null;
        while ((curRow = readLine()) != null){
            //mapReduce.reduce();
        }
    }
    public String readLine(){
        if(dataBuffer == null || dataBuffer.length == 0 || count == dataBuffer.length){
            return null;
        }

        StringBuffer stringBuffer = new StringBuffer();
        char ch;
        while ((ch = (char) dataBuffer[count++]) != '\n'){
            stringBuffer.append(ch);
        }
        String s = stringBuffer.toString();
        if(s.length() == 0){
            return null;
        }else{
            return s;
        }
    }
}
