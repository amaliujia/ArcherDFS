package MapReduce.MapReduceIO;

import FileSystem.Base.SDDFSNode;

/**
 * Created by amaliujia on 15-5-25.
 */
public class SDDFSDataBlock {
    private long chunkID;
    private SDDFSNode[] nodes;
    private long offset;
    private int size;
    private int count;

    public SDDFSDataBlock(long chunkID, SDDFSNode[] nodes, long offset, int size) {
        this.chunkID = chunkID;
        this.nodes = nodes;
        this.offset = offset;
        this.size = size;
        count = 0;
    }

    public long getChunkID(){
        return chunkID;
    }

    public SDDFSNode getNextNode(){
        if(count < nodes.length){
            SDDFSNode node = nodes[count];
            count++;
            return node;
        }
        return null;
    }

    public long getOffset(){
        return offset;
    }

    public int getSize(){
        return size;
    }
}
