package MapReduce.MapReduceIO;

/**
 * Created by amaliujia on 15-5-12.
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
}
