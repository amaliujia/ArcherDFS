package MapReduce.Abstraction;

import java.util.Iterator;

/**
 * Created by amaliujia on 15-5-9.
 */
public interface SDReducer<K2, V2, K3, V3> {
    void reduce(K2 key, Iterator<V2> values, SDOutputCollector<K3, V3> output);

}

