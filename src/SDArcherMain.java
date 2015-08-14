import MapReduce.JobTracker.SDJobTracker;
import MapReduce.TaskTracker.SDTaskTracker;
import Master.SDMasterNode;
import Slave.SDSlaveNode;
import Util.SDUtil;

/**
 * Created by amaliujia on 14-12-20.
 */
public class SDArcherMain {
    public static void main(String[] args) throws Exception {
        // initialize log4j
        org.apache.log4j.BasicConfigurator.configure();

        // launch module.
        if(args.length == 0) {
            SDUtil.fatalError("Wrong arguments for main function");
        }else if(args[0].equals("master")){
            //start master
            SDMasterNode master = new SDMasterNode();
            master.startService();

            //start map reduce jobtracker service
            SDJobTracker jobTracker = new SDJobTracker();
            jobTracker.startService();
        }else if(args[0].equals("slave")){
            SDSlaveNode slave = new SDSlaveNode();
            slave.startService();

            SDTaskTracker taskTracker = new SDTaskTracker();
            taskTracker.startService();
        }else if(args[0].equals("RMIServer")){
            //TODO: start rmiserver
        }else if(args[0].equals("Registry")){
            //TODO: start registry
        }else if(args[0].equals("JobTracker")){

        }else{
            System.out.println("Arguments wrong");
        }
    }
}
