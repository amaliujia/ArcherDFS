package MapReduce.JobTracker;

/**
 * @author amaliujia
 */
public class SDJobInitializationUnit implements Runnable {

    private SDJobUnit jobUnit;

    private SDJobTracker jobTracker;

    public SDJobInitializationUnit(SDJobUnit unit, SDJobTracker tracker){
        this.jobUnit = unit;
        this.jobTracker = tracker;
    }

    private void splitInputFile(){

    }

    private void setupMapperTask(){

    }

    public void run() {
        // start to initialize MapReduce job

        setupMapperTask();

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
