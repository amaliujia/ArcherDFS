package FileSystem.Base;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by amaliujia on 14-12-27.
 */
public class SDFileChunk implements Serializable {

    public static AtomicLong maxId = new AtomicLong(0);

    private long id;

    private long fileID;

    private long offset;

    private int size;

    private Set<SDDFSNode> chunkNodes;

    public SDFileChunk(long id, long fileID, long offset, int size, SDDFSNode[] nodes){
        this.id = id;
        this.fileID = fileID;
        this.size = size;
        this.chunkNodes = new TreeSet<SDDFSNode>(Arrays.asList(nodes));
    }

    public long getId(){
        return this.id;
    }

    public long getFileID(){
        return this.fileID;
    }
}
