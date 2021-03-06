package MapReduce.TaskTracker;

import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDReducerTask;
import MapReduce.Util.SDMapReduceConstant;
import Protocol.MapReduce.SDJobService;
import Protocol.MapReduce.SDTaskService;
import Util.SDUtil;

import java.io.File;
import java.io.RandomAccessFile;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.concurrent.*;

/**
 * The "slave" for MapReduce.
 *
 * @author amaliujia
 */
public class SDTaskTracker {
    private ScheduledExecutorService heartbeatService;
    private SDJobService jobService;
    private SDTaskService taskService;
    private Registry registry;
    private int numMapperTasks;
    private int numReducerTasks;
    private ExecutorService threadPool;
    private ConcurrentHashMap<Integer, SDTaskExecuteReducerWorker> reducerTaskWorkers;
    private String lock = "counterlock";

    public void startService() throws RemoteException, NotBoundException {

        numMapperTasks = 0;
        numReducerTasks = 0;

        registry = LocateRegistry.getRegistry(SDUtil.SALVE_RMIREGISTRY_PORT);
        taskService = new SDTaskTrackerRMIService(this);
        registry.rebind(SDTaskTracker.class.getCanonicalName(), taskService);
        registry = LocateRegistry.getRegistry(SDUtil.masterAddress, SDUtil.MASTER_RMIRegistry_PORT);
        jobService = (SDJobService) registry.lookup(SDJobService.class.getCanonicalName());

        heartbeatService = Executors.newScheduledThreadPool(3);
        heartbeatService.scheduleAtFixedRate(new SDTaskHeartbeatJob(jobService, this),
                0, SDUtil.HEARTBEAT_PERIOD_SEC, TimeUnit.SECONDS);

        // TODO:: number of threads in pool needs tuning.
        threadPool = Executors.newScheduledThreadPool(5);

        reducerTaskWorkers = new ConcurrentHashMap<Integer, SDTaskExecuteReducerWorker>();
    }

    public void runMapperTask(SDMapperTask task){
        task.setOutputDir(SDMapReduceConstant.MAP_OUTPUT_DIR);
        increaseNumOfMapper();
        //ready to run
        threadPool.execute(new SDTaskExecuteMapperWorker(this, task));
    }

    public int getNumMapperTasks(){
        return numMapperTasks;
    }

    public int getNumReducerTasks(){
        return numReducerTasks;
    }

    public void mapreduceTaskSucceed(SDMapperTask task){
        try {
            jobService.mapperTaskSucceed(task);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        decreaseNumOfMapper();
    }

    public void mapperTaskFail(SDMapperTask task){
        try {
            jobService.mapperTaskFailed(task);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        decreaseNumOfMapper();
    }

    public void reducerTaskFail(SDReducerTask task){
        try {
            jobService.reducerTaskFailed(task);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        decreaseNumofReducer();
    }

    public void reducerTaskSucceed(SDReducerTask task){
        try {
            jobService.reducerTaskSucceed(task);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        decreaseNumofReducer();
    }

    private void decreaseNumOfMapper(){
        synchronized (lock){
            numMapperTasks--;
        }
    }

    private void increaseNumOfMapper(){
        synchronized (lock){
            numMapperTasks++;
        }
    }

    private void decreaseNumofReducer(){
        synchronized (lock){
            numReducerTasks--;
        }
    }

    private void increaseNumOfReducer(){
        synchronized (lock){
            numReducerTasks++;
        }
    }

    public void runReducerTask(SDReducerTask reducerTask, SDMapperTask mapperTask){
        increaseNumOfReducer();
        SDTaskExecuteReducerWorker worker = null;
        if(reducerTaskWorkers.containsKey(reducerTask.getTaskID())){
            worker = reducerTaskWorkers.get(reducerTask.getTaskID());
            worker.addMapperTask(mapperTask);
            if(worker.ifRun()) {
                threadPool.execute(worker);
            }
        }else{
            worker = new SDTaskExecuteReducerWorker(reducerTask, this);
            worker.addMapperTask(mapperTask);
            reducerTaskWorkers.put(reducerTask.getTaskID(), worker);
            if(worker.ifRun()) {
                threadPool.execute(worker);
            }
        }
    }

    public byte[] getsShards(String filename) throws RemoteException{
        File file = new File(filename);
        if(file.exists()){
            try {
                RandomAccessFile rFile = new RandomAccessFile(file, "r");
                int size = (int) rFile.length();
                byte[] readBuffer = new byte[size];
                int len = rFile.read(readBuffer, 0, size);
                if (len > 0) {
                    byte[] data = Arrays.copyOf(readBuffer, len);
                    rFile.close();
                    return data;
                } else {
                    return null;
                }
            } catch (Exception e){
                throw new RemoteException();
            }
        }
        return null;

    }
}
