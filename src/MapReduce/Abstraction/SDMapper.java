package MapReduce.Abstraction;

/**
 * Interface of map function.
 *
 * @author amaliujia
 */
public interface SDMapper{
    void map(String value, SDOutputCollector output);
}
