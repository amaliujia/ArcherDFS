package FileSystem.Base;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author amaliujia
 */
public class SDFileChunk implements Serializable, Comparable<SDFileChunk> {
    // ID producer
    public static AtomicLong maxId = new AtomicLong(0);

    // chunk ID
    private long id;

    // File
    private long fileID;

    private long offset;

    private int size;

    private Set<SDDFSNode> chunkNodes;

    public SDFileChunk(long id, long fileID, long offset, int size, SDDFSNode[] nodes){
        this.id = id;
        this.fileID = fileID;
        this.size = size;
        this.offset = offset;
        this.chunkNodes = new TreeSet<SDDFSNode>(Arrays.asList(nodes));
    }

    public long getId(){
        return this.id;
    }

    public long getFileID(){
        return this.fileID;
    }

    public long getOffset() {
        return offset;
    }

    public int getSize(){
        return  size;
    }

    public Set<SDDFSNode> getChunkNodes(){
        return chunkNodes;
    }

    public SDDFSNode[] getChunkNodesInArray(){
        return (SDDFSNode[]) chunkNodes.toArray();
    }

    public int compareTo(SDFileChunk o) {
        return 0;
    }

    public String toString(){
        return id + " in " + fileID + " begins at " + offset + " with size " + size;
    }
}
