package FileSystem.Master;

import FileSystem.Base.SDDFSFile;
import FileSystem.Base.SDDFSNode;
import FileSystem.Base.SDFileChunk;
import Logging.SDLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by amaliujia on 14-12-28.
 */
public class SDIndex {
    private static Logger logger = LoggerFactory.getLogger(SDIndex.class);

    private String lock;

    // file to data nodes
    private Map<String, SDDFSNode> dataNodes;

    //file to file id
    private Map<String, Long> fileIndex;

    //file id to file instances
    private Map<Long, SDDFSFile> files;

    //chunk id to chunk instances
    private Map<Long, SDFileChunk> chunks;

    //operation log
    private SDLogger sdLogger;

    public SDIndex(SDLogger sdLogger){
        this.lock = "SkyDragon";
        this.sdLogger = sdLogger;
        this.dataNodes = new HashMap<String, SDDFSNode>();
        this.fileIndex = new HashMap<String, Long>();
        this.chunks = new HashMap<Long, SDFileChunk>();
        this.files = new HashMap<Long, SDDFSFile>();
    }

    public void updateDataNode(String serviceName, String registryHost, int registryPort,
                               int numChunker, long timestamp, boolean logable){

    }

    public void removeDataNode(String serviceName, boolean logable){}

    public SDDFSFile createFile(String fileName, int replication, boolean logable){
        return null;
    }

    public SDDFSFile getFile(String fileName){
        SDDFSFile file = null;
        synchronized (lock){
           if(fileIndex.containsKey(fileName)){
               file = files.get(fileIndex.get(fileName));
           }
        }
        return file;
    }

    public SDDFSFile[] listFiles(){
        SDDFSFile[] fs = null;

        synchronized (lock){
            fs = new SDDFSFile[files.size()];
            files.values().toArray(fs);
        }
        return fs;
    }

    public void deleteFile(String serviceName, boolean logable){

    }


    //TODO: log recover

}
