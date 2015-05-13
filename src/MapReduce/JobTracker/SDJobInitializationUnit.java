package MapReduce.JobTracker;

import MapReduce.DispatchUnits.SDJobStatus;
import MapReduce.DispatchUnits.SDMapperTask;
import MapReduce.MapReduceIO.SDFileSegment;
import MapReduce.MapReduceIO.SDSplitAgent;
import Util.SDUtil;
import org.apache.log4j.Logger;

import java.util.List;

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

            jobTracker.addMapperTask(task);
        }
//        for(FileBlock fileBlock : fileBlocks){
//            MapperTask task = new MapperTask(job.getId(), fileBlock, job.getConfig().getReducerAmount());
//            TaskTrackerInfo taskTracker = getMapperTaskTracker();
//            if(taskTracker == null){
//                job.setJobStatus(JobStatus.FAILED);
//                throw new RemoteException("No available task tracker now");
//            }
//            task.setTaskTrackerName(taskTracker);
//            task.setStatus(TaskStatus.PENDING);
//            task.setMRClassName(job.getConfig().getClassName());
//            job.addMapperTask(task);
//        }
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

        //TODO: set up reducer task
    }

    //    private void generateMapperTasks(JobInfo job)
//            throws Exception {
//        List<FileBlock> fileBlocks = splitInputFile(job);
//        job.getConfig().setMapperAmount(fileBlocks.size());
//        for(FileBlock fileBlock : fileBlocks){
//            MapperTask task = new MapperTask(job.getId(), fileBlock, job.getConfig().getReducerAmount());
//            TaskTrackerInfo taskTracker = getMapperTaskTracker();
//            if(taskTracker == null){
//                job.setJobStatus(JobStatus.FAILED);
//                throw new RemoteException("No available task tracker now");
//            }
//            task.setTaskTrackerName(taskTracker);
//            task.setStatus(TaskStatus.PENDING);
//            task.setMRClassName(job.getConfig().getClassName());
//            job.addMapperTask(task);
//        }
//    }
}
