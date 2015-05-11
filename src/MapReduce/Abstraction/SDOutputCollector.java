package MapReduce.Abstraction;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author amaliujia
 */
public class SDOutputCollector<K, V> implements Serializable{
    private PriorityQueue<Pair<K, V>> priorityQueue;
    private Set<K> keys;

    public SDOutputCollector(){
        priorityQueue = new PriorityQueue<Pair<K, V>>();
        keys = new TreeSet<K>();
    }

    public void collect(K key, V value){
        priorityQueue.add(new Pair<K, V>(key, value));
        keys.add(key);
    }
}
