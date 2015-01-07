package MapReduce.Common;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by amaliujia on 15-1-7.
 */
public interface SDMapReduce extends Serializable{
    // map function
    public void map(String key, String value, SDOutputCollector collector);

    // reduce function
    public void reduce(String key, Iterator<String> values, SDOutputCollector collector);
}
