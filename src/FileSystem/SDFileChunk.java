package FileSystem;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by amaliujia on 14-12-27.
 */
public class SDFileChunk implements Serializable {
    private long id;

    private long fileID;

    private long offset;

    private int size;

    private Set<SDDFSNode> chunkNodes;

    public SDFileChunk(long id, long fileID, int size, SDDFSFile[] nodes){
        this.id = id;
        this.fileID = fileID;
        this.size = size;
        this.chunkNodes = new TreeSet<SDDFSNode>(Arrays.asList(nodes));
    }

}
