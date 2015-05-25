package MapReduce.MapReduceIO;

/**
 * @author amaliujia
 */
public class SDFileSegment {
    private String filename;
    private int size;
    private long offset;

    public SDFileSegment(String name, int s, long o){
        filename = name;
        size = s;
        offset = o;
    }

    public String getFilename(){
        return filename;
    }

    public int getSize(){
        return size;
    }

    public long getOffset(){
        return offset;
    }
}
