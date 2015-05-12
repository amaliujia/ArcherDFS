package MapReduce.MapReduceIO;

import ArcherException.SDUnmarshallingException;
import FileSystem.Base.SDDFSFile;
import FileSystem.Base.SDDFSNode;
import FileSystem.Base.SDFileChunk;
import Protocol.DFS.MasterService.SDMasterService;
import Protocol.DFS.SlaveService.SDSlaveService;
import Util.SDUtil;
import com.sun.deploy.util.ArrayUtil;
import org.apache.log4j.Logger;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a singleton. DFSController is designed as an abstraction, for communicating with
 * DFS.
 * @author amaliujia
 */
public class SDDFSController {
    public static Logger LOG4jLogger = Logger.getLogger(SDDFSController.class);

    private static SDDFSController client = null;

    private String host;
    private int port;
    private SDMasterService masterService;

    public static SDDFSController instance(){
        if(client == null){
            LOG4jLogger.info(SDUtil.LOG4JINFO_MAPREDUCE + "initialize DFS controller");
            client.host = SDUtil.masterAddress;
            client.port = SDUtil.MASTER_RMIRegistry_PORT;
            client.connect();
        }
        return client;
    }

    /**
     * Get stub of DFS master RMI service.
     */
    private void connect(){
        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            masterService = (SDMasterService)registry.lookup(SDMasterService.class.getCanonicalName());
        } catch (RemoteException e) {
            LOG4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + "DFS Controller cannot get stub of DFS master RMI service");
            e.printStackTrace();
        } catch (NotBoundException e) {
            LOG4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + SDMasterService.class.getCanonicalName() + " doesn't bount");
            e.printStackTrace();
        }
    }

    /**
     * Get descriptor of file in DFS.
     * @param filename
     *         name of input file.
     * @return
     *          Descriptor of input file in DFS.
     * @throws RemoteException
     *          throws when remote error.
     */
    private SDDFSFile getDFSFileDescriptor(String filename) throws RemoteException {
        return masterService.getFile(filename);
    }


    /**
     *  This function is trying to split input file into lines by "\n", return offsets of each line.
     * @param fileName
     *         name of input file.
     * @return
     *      long[]: offsets of lines.
     *      null: nothing.
     * @throws RemoteException
     *      throws when remote error.
     */
    public long[] splitLines(String fileName)
            throws RemoteException{
        SDDFSFile file = getDFSFileDescriptor(fileName);
        if(file == null){
            LOG4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + fileName + " doesn't exit in DFS");
            return null;
        }
        SDFileChunk[] chunks = file.getChunks();
        if(chunks == null){
            LOG4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + file.toString() + " doesn't have chunks");
            return null;
        }
        List<Long> offsets = new ArrayList<Long>();
        for(SDFileChunk c : chunks){
            long[] linesinChunk = splitLinesInChunk(c);
            if(linesinChunk == null){
                LOG4jLogger.error(SDUtil.LOG4JERROR_MAPREDUCE + c.toString() + " doesn't have data");
                return null;
            }
            for(long off : linesinChunk){
                offsets.add(c.getOffset() + off);
            }
        }
        Long[] results = new Long[offsets.size()];
        offsets.toArray(results);
        return SDUtil.toPrimitives(results);
    }

    private long[] splitLinesInReplica(SDDFSNode n, long id)
            throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(n.getRegistryHost(), n.getRegistryPort());
        SDSlaveService slaveService = (SDSlaveService ) registry.lookup(n.getServiceName());
        return slaveService.offsetOfLinesInChunk(id);
    }

    /**
     * This function is trying to split chunk into lines by "\n", return offsets of each line.
     * @param chunk
     *      Descriptor of chunk in DFS.
     * @return
     *      long[]: offsets of lines.
     *      null: nothing.
     */
    private long[] splitLinesInChunk(SDFileChunk chunk){
        SDDFSNode[] nodes = chunk.getChunkNodesInArray();
        long[] results = null;
        for(SDDFSNode node : nodes){
            try {
                results = splitLinesInReplica(node, chunk.getId());
                if (results == null){
                    continue;
                }else{
                    return results;
                }
            } catch (RemoteException e) {
                continue;
            } catch (NotBoundException e) {
                continue;
            }
        }
        return results;
    }
}
