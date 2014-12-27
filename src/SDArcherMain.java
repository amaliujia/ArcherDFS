import Master.SDMasterNode;
import Slave.HKSlaveNode;
import Util.SDUtil;

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
            master.startService(SDUtil.masterLinstenerPort);
        }else if(args[0].equals("slave")){
            //start salve
            HKSlaveNode slave = new HKSlaveNode(args[1], Integer.parseInt(args[2]));
            slave.connect();
            new Thread(slave).start();
        }else if(args[0].equals("RMIServer")){
            //TODO: start rmiserver
        }else if(args[0].equals("Registry")){
            //TODO: start registry
        }else{
            System.out.println("Arguments wrong");
        }
    }
}
