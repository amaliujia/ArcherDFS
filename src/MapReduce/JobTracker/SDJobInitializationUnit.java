package MapReduce.JobTracker;

import MapReduce.DispatchUnits.SDJobStatus;
import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.DispatchUnits.SDReducerTask;
import MapReduce.DispatchUnits.SDTaskStatus;
import MapReduce.MapReduceIO.SDFileSegment;
import MapReduce.MapReduceIO.SDSplitAgent;
import MapReduce.TaskTracker.SDRemoteTaskObject;
import Util.SDUtil;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author amaliujia
 */
public class SDJobInitializationUnit implements Runnable {
    public static Logger Log4jLogger = Logger.getLogger(SDJobInitializationUnit.class);

    private SDJobUnit jobUnit;

    private SDJobTracker jobTracker;

    public SDJobInitializationUnit(SDJobUnit unit, SDJobTracker tracker){
        this.jobUnit = unit;
        this.jobTracker = tracker;
    }

    private List<SDFileSegment> splitInputFile(){
        SDSplitAgent agent = new SDSplitAgent();
        return agent.split(jobUnit.getJobConfig().getInputFile(), jobUnit.getJobConfig().getNumMapper());
    }

    private void setupMapperTask() throws Exception{
        Log4jLogger.info(SDUtil.LOG4JINFO_MAPREDUCE +  jobUnit.getJobConfig().getJobName() + ", set up mapper task");
        List<SDFileSegment> segments = splitInputFile();
        if(segments == null){
            jobUnit.setJobStatus(SDJobStatus.FAIL);
            Log4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + "cannot split input file");
            throw new Exception("Split fail");
        }

        //alloc mapper tasks
        for (SDFileSegment segment : segments){
            SDMapperTask task = new SDMapperTask(jobUnit.getID(), segment);
            //init
            SDRemoteTaskObject o = jobTracker.getMapperTaskTracker();
            if(o == null){
                Log4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + "No available task tracker");
                throw new Exception("No available task tracker");
            }
            task.setTaskTracker(o);
            task.setTaskStatus(SDTaskStatus.PENDING);
            jobUnit.addMapperTask(task);
        }
    }

    private void  setupReducerTask() throws Exception {
        Log4jLogger.info(SDUtil.LOG4JINFO_MAPREDUCE +  jobUnit.getJobConfig().getJobName() + ", set up reducer task");
        int reducerNum = jobUnit.getJobConfig().getNumReducer();

    }

    private void dispatchMapperTask(SDMapperTask t){

    }

    private void dispatchTasks(){
        Map<Integer, SDMapperTask> m = jobUnit.getMapperTaskMap();
        Iterator<SDMapperTask> iterator = m.values().iterator();
        while (iterator.hasNext()){
            SDMapperTask s = iterator.next();
        }
    }

    public void run() {
        // start to initialize MapReduce job
        jobUnit.setJobStatus(SDJobStatus.SETUP);
        try {
            setupMapperTask();

        } catch (Exception e) {
            jobUnit.setJobStatus(SDJobStatus.FAIL);
            return;
        }

        try {
            setupMapperTask();
        } catch (Exception e) {
            jobUnit.setJobStatus(SDJobStatus.FAIL);
            return;
        }

        //TODO: add task into job tracker queue.


        jobUnit.setJobStatus(SDJobStatus.PENDING);
    }

}
