import Master.SDMasterNode;
import Slave.SDSlaveNode;
import Util.SDUtil;
import org.apache.log4j.jmx.LoggerDynamicMBean;

import java.util.logging.Logger;

/**
 * Created by amaliujia on 14-12-20.
 */
public class SDArcherMain {
    public static void main(String[] args) throws Exception {

        if(args.length == 0) {
            SDUtil.fatalError("Wrong arguments for main function");
        }else if(args[0].equals("master")){
            //start master
            SDMasterNode master = new SDMasterNode();
            master.startService();
        }else if(args[0].equals("slave")){
            SDSlaveNode slave = new SDSlaveNode();
            slave.startService();
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
