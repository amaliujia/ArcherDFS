package FileSystem.Base;

import Util.SDUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by amaliujia on 14-12-27.
 */
public class SDDFSFile implements Serializable {
    private long fileID;

    private String name;

    private int replication;

    private Set<SDFileChunk> chunks;

    public SDDFSFile(long id, String name){
        this(id, name, SDUtil.DEFAULT_REPLICATION);
    }

    public SDDFSFile(long id, String name, int replication){
        this.fileID = id;
        this.name = name;
        this.replication = replication;
        this.chunks = new TreeSet<SDFileChunk>();
    }

    public void addChunk(SDFileChunk chunk){
        chunks.add(chunk);
    }

    public SDFileChunk[] getChunks(){
        SDFileChunk[] chunks = new SDFileChunk[this.chunks.size()];
        this.chunks.toArray(chunks);
        Arrays.sort(chunks);
        return chunks;
    }
    public void removeChunk(SDFileChunk chunk){
        this.chunks.remove(chunk);
    }

}
