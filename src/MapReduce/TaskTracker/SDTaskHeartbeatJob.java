package MapReduce.TaskTracker;

import Protocol.MapReduce.SDJobService;
import Util.SDUtil;

import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

/**
 * Created by amaliujia on 15-5-13.
 */
public class SDTaskHeartbeatJob implements Runnable {
    private SDTaskTracker taskTracker;
    private Registry registry;

    public SDTaskHeartbeatJob(Registry registry, SDTaskTracker taskTracker){
        this.registry = registry;
        this.taskTracker = taskTracker;
    }

    public void run() {
        try {
            SDJobService service = (SDJobService) registry.lookup(SDJobService.class.getCanonicalName());
            SDRemoteTaskObject object = new SDRemoteTaskObject(SDUtil.getlocalHost(), SDUtil.SALVE_RMIREGISTRY_PORT);
            object.setMapperTaskNumber(taskTracker.getNumMapperTasks());
            object.setReduceTaskNumber(taskTracker.getNumReducerTasks());
            service.heartbeat(object);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
