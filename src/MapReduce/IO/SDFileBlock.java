package MapReduce.IO;

/**
 * Created by amaliujia on 15-1-9.
 */
public class SDFileBlock {

    private String file;

    private long offset;

    private long size;

    public SDFileBlock(String file, long offset, long size){
        this.file = file;
        this.offset = offset;
        this.size = size;
    }

    public String getFile() {
        return file;
    }

    public long getOffset() {
        return offset;
    }

    public long getSize() {
        return size;
    }
}
