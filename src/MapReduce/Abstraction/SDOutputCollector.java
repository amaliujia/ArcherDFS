package MapReduce.Abstraction;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

/**
 * Output collector for map function.
 *
 * @author amaliujia
 */
public class SDOutputCollector implements Serializable{
    private PriorityQueue<Pair<String, String>> priorityQueue;
    private Set<String> keys;

    public SDOutputCollector(){
        priorityQueue = new PriorityQueue<Pair<String, String>>();
        keys = new TreeSet<String>();
    }

    public void collect(String key, String value){
        priorityQueue.add(new Pair<String, String>(key, value));
        keys.add(key);
    }
}
