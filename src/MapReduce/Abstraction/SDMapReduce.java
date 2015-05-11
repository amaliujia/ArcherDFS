package MapReduce.Abstraction;

import MapReduce.Application.SDInstance;
import MapReduce.JobTracker.SDJobConfig;
import Util.SDUtil;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;

/**
 * SDMapRecue is a abstract class. This class is used for user to
 * define their own map and reduce functions. In the meantime, this
 * class also gives a entrance to run users' self-defined class.
 *
 * @author amaliujia
 */
public abstract class SDMapReduce implements SDMapper, SDReducer, Serializable {

    private static Logger logger = Logger.getLogger(SDMapReduce.class);

    public abstract void map(Object key, Object value, SDOutputCollector output);

    public abstract void reduce(Object key, Iterator values, SDOutputCollector output);

    /**
     * Entrance for execute user-defined map reduce.
     * @param args
     *         args[0] jobName
     *         args[1] user-defined class name
     *         args[2] user inputfile
     *         args[3] uesr outputfile
     *         args[4] mapper number
     *         args[5] reducer number
     */
    public void run(String[] args){
        SDJobConfig jobConfig = new SDJobConfig(args[0], args[1], args[2], args[3],
                                            Integer.parseInt(args[4]), Integer.parseInt(args[5])
                                            , this.getClass());

        SDInstance instance = new SDInstance(SDUtil.masterAddress, SDUtil.MASTER_RMIRegistry_PORT);
        try {
            instance.run(jobConfig);
        } catch (RemoteException e) {
            logger.error("SDMapreduce instance " + args[0] + " fail: remote exception " + e.getMessage());
        } catch (NotBoundException e) {
            logger.error("SDMapreduce instance " + args[0] + " fail: service not bound " + e.getMessage());
        }
    }

}
