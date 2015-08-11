package MapReduce.TaskTracker;

import Protocol.MapReduce.SDJobService;
import Util.SDUtil;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.rmi.RemoteException;

/**
 * Created by amaliujia on 15-5-13.
 */
public class SDTaskHeartbeatJob implements Runnable {
    private SDTaskTracker taskTracker;
    private SDJobService service;

    public SDTaskHeartbeatJob(SDJobService jobService, SDTaskTracker taskTracker){
        this.service = jobService;
        this.taskTracker = taskTracker;
    }

    public void run() {
        try {
            SDRemoteTaskObject object = new SDRemoteTaskObject(SDUtil.getlocalHost(), SDUtil.SALVE_RMIREGISTRY_PORT);
            object.setMapperTaskNumber(taskTracker.getNumMapperTasks());
            Logger Log4jLogger = Logger.getLogger(SDTaskHeartbeatJob.class);
            Log4jLogger.debug(SDUtil.LOG4JINFO_MAPREDUCE + object.toString() + " sends heart beat");
            service.heartbeat(object);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
