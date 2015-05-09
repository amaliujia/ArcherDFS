package MapReduce.Abstraction;

import javafx.util.Pair;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by amaliujia on 15-5-9.
 */
public class SDOutputCollector<K, V> {
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
