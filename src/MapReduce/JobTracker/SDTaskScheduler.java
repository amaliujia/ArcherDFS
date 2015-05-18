package MapReduce.JobTracker;

import MapReduce.DispatchUnits.SDMapperTask;
import Util.SDUtil;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  @author amaliujia
 */
public class SDTaskScheduler implements Runnable {
    public static Logger Log4jLogger = Logger.getLogger(SDTaskScheduler.class);

    private SDJobTracker jobTracker;
    private ExecutorService threadPool;

    public SDTaskScheduler(SDJobTracker jobTracker){
        this.jobTracker = jobTracker;
        threadPool = Executors.newScheduledThreadPool(5);
    }

    public void run() {
        while (true){
            try {
                SDMapperTask task = jobTracker.getMapperTaskInQueue();
                SDTaskExecuteUnit executeUnit = new SDTaskExecuteUnit(jobTracker, task);
                threadPool.execute(executeUnit);
            }catch (InterruptedException e){
                Log4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + "task scheduler interrupted");
                System.exit(1);
            }
        }
    }
}
