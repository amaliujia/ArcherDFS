package FileSystem.Base;

import Util.SDUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by amaliujia on 14-12-27.
 */
public class SDDFSFile implements Serializable{
    public static AtomicLong maxId = new AtomicLong(0);

    // file ID of logic file in distributed system.
    private long fileID;

    //file path
    private String name;

    // replication number
    private int replication;

    // chunks of this file
    private List<SDFileChunk> chunks;
    public SDDFSFile(long id, String name){
        this(id, name, SDUtil.DEFAULT_REPLICATION);
    }

    public SDDFSFile(long id, String name, int replication){
        this.fileID = id;
        this.name = name;
        this.replication = replication;
        this.chunks = new ArrayList<SDFileChunk>();
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

    public long getFileID(){
        return this.fileID;
    }

    public int getReplication() {
        return this.replication;
    }

    public String getFileName(){
        return name;
    }

    public String toString(){
        return "ID: " + fileID + " name: " + name + " replicas: " + replication;
    }
}
