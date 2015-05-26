package MapReduce.Abstraction;

import java.util.Iterator;

/**
 * Interface of reduce function.
 *
 * @author amaliujia
 */
public interface SDReducer {
    void reduce(String key, Iterator<String> values, SDOutputCollector output);

}

