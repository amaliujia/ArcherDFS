package MapReduce.Abstraction;

/**
 * Created by amaliujia on 15-5-9.
 */
public interface SDMapper<K1, V1, K2, V2>{
    void map(K1 key, V1 value, SDOutputCollector<K2, V2> output);
}
