package MapReduce.Abstraction;

/**
 * @author amaliujia
 */
public interface SDMapper<K1, V1, K2, V2>{
    void map(K1 key, V1 value, SDOutputCollector<K2, V2> output);
}
