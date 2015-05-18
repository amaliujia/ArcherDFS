package MapReduce.JobTracker;

import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDTaskStatus;
import MapReduce.TaskTracker.SDRemoteTaskObject;
import Protocol.MapReduce.SDTaskService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author amaliujia
 */
public class SDTaskExecuteUnit implements Runnable {
    private SDMapperTask mapperTask;
    private SDJobTracker jobTracker;

    public SDTaskExecuteUnit(SDJobTracker jobTracker, SDMapperTask task){
        this.jobTracker = jobTracker;
        this.mapperTask = task;
    }

    public void run() {
        if (mapperTask.getTaskStatus() == SDTaskStatus.PENDING){
            SDRemoteTaskObject taskObject = mapperTask.getTaskTracker();
            try {
                Registry registry = LocateRegistry.getRegistry(taskObject.getHostname(), taskObject.getHostport());
                //if service name is ok?
                SDTaskService service = (SDTaskService)registry.lookup(SDTaskService.class.getCanonicalName());
                service.runMapperTask(mapperTask);
            } catch (Exception e) {
                //Add to fail
            }
        }
    }
}
