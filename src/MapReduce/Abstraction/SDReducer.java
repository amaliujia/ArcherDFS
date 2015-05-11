package MapReduce.Abstraction;

import java.util.Iterator;

/**
 * Interface of reduce function.
 *
 * @author amaliujia
 */
public interface SDReducer<K2, V2, K3, V3> {
    void reduce(K2 key, Iterator<V2> values, SDOutputCollector<K3, V3> output);

}

