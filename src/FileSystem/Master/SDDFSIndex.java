package FileSystem.Master;

import FileSystem.Base.SDDFSFile;
import FileSystem.Base.SDDFSNode;
import FileSystem.Base.SDFileChunk;
import FileSystem.Util.SDDFSConstants;
import Logging.SDLogOperation;
import Logging.SDLogger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * Created by amaliujia on 14-12-28.
 */
public class SDDFSIndex {
//    private static Logger logger = LoggerFactory.getLogger(SDDFSIndex.class);

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

    public SDDFSIndex(SDLogger sdLogger){
        this.lock = "SkyDragon";
        this.sdLogger = sdLogger;
        this.dataNodes = new HashMap<String, SDDFSNode>();
        this.fileIndex = new HashMap<String, Long>();
        this.chunks = new HashMap<Long, SDFileChunk>();
        this.files = new HashMap<Long, SDDFSFile>();
    }

    public void updateDataNode(String serviceName, String registryHost, int registryPort,
                               int numChunker, long timestamp, boolean logable){
        SDDFSNode dataNode = null;
        synchronized (lock){
            if(dataNodes.containsKey(serviceName)){
                dataNode = dataNodes.get(serviceName);
                //TODO: how to log it?

            } else{
                 dataNode = new SDDFSNode(serviceName, registryHost, registryPort);
                 dataNodes.put(serviceName, dataNode);
                 if(logable){
                    dfsLog(SDLogOperation.UPDATE_DATA_NODE, new Object[] {serviceName,
                            registryHost, registryPort, numChunker}) ;
                }
            }
            dataNode.setChunkNumber(numChunker);
            dataNode.setTimestamp(timestamp);
            System.out.println(dataNode.toString());
        }
    }

    public void removeDataNode(String serviceName, boolean logable){
        synchronized (lock){
            this.dataNodes.remove(serviceName);
            if(logable){
                dfsLog(SDLogOperation.REMOVE_DATA_NODE, new Object[] {serviceName});
            }
        }
    }

    public SDDFSFile createFile(String fileName, int replication, boolean logable){
        SDDFSFile file = null;

        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(fileName, "r");
        } catch (FileNotFoundException e) {
            System.err.println(fileName + " doesn't exist" );
            return file;
        }

        synchronized (lock){
            if(logable){
                dfsLog(SDLogOperation.DFS_CREATE_FILE, new Object[] {fileName, replication});
            }
            if(fileIndex.containsKey(fileName)){
                deleteFile(fileName, true);
            }
            file = new SDDFSFile(SDDFSFile.maxId.incrementAndGet(), fileName);
            fileIndex.put(fileName, file.getFileID());
            files.put(file.getFileID(), file);
        }


        try {
            long filesize = randomAccessFile.length();
            int chunknumToSplit = (int)(filesize / SDDFSConstants.CHUNK_SIZE);
            long lastOff = filesize - chunknumToSplit * SDDFSConstants.CHUNK_SIZE;
            for(int i = 0; i < chunknumToSplit ; i++){
                createChunk(file.getFileID(), i * SDDFSConstants.CHUNK_SIZE, (int)SDDFSConstants.CHUNK_SIZE, true);
            }
            if(filesize - lastOff > 0){
                createChunk(file.getFileID(), lastOff, (int)(filesize - lastOff), true);
            }
        } catch (IOException e) {
            System.err.println(fileName + " cannot be modified");
            return file;
        }

        return file;
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
        synchronized (lock){
            if(logable){
                dfsLog(SDLogOperation.DFS_DELETE_FILE, new Object[] {serviceName});
            }
            if(fileIndex.containsKey(serviceName)){
                long id = fileIndex.get(serviceName);
                fileIndex.remove(serviceName);
                files.remove(id);
            }
        }
    }

    private SDFileChunk createChunk(long fileId, long offset, int size, boolean logable){
        SDFileChunk chunk = null;
        synchronized (lock){
            if(logable){
                dfsLog(SDLogOperation.DFS_CREATE_CHUNK, new Object[]{fileId, offset, size});
            }
            if(files.containsKey(fileId)){
                SDDFSFile file = files.get(fileId);
                SDDFSNode[] dataNodes = allocateNode(file.getReplication());
                long chunkId = SDFileChunk.maxId.incrementAndGet();
                chunk = new SDFileChunk(chunkId, fileId, offset, size, dataNodes);
                file.addChunk(chunk);
                chunks.put(chunk.getId(), chunk);
            }
        }
        return chunk;
    }

    private void dfsLog(byte operationType, Object[] arguments){
        SDLogOperation operation = new SDLogOperation(operationType, arguments);
        sdLogger.writeLog(operation);
    }

    private SDDFSNode[] allocateNode(int replication) {
        List<SDDFSNode> nodes = new ArrayList<SDDFSNode>();
        SDDFSNode[] results = null;
        SDDFSNode[] allNodes = new SDDFSNode[dataNodes.size()];
        dataNodes.values().toArray(allNodes);
        Arrays.sort(allNodes, new Comparator<SDDFSNode>() {
            @Override
            public int compare(SDDFSNode o1, SDDFSNode o2) {
                return o1.getChunkNumber() - o2.getChunkNumber();
            }
        });
//        for(int i = 0; i < replicas && i < allNodes.length; i++){
//            if(allNodes[i].isValid()){
//                nodes.add(allNodes[i]);
//            }
//        }
        nodes.add(allNodes[0]);
        results = new SDDFSNode[nodes.size()];
        nodes.toArray(results);
        return results;
    }

    //TODO: log recover
}
