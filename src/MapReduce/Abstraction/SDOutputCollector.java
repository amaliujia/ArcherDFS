package MapReduce.Abstraction;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.*;

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

    /**
     * Interface provided to users. Accept user's map/reduce output.
     * @param key
     *          key for < key, value > pair.
     * @param value
     *          value for < key, value > pair.
     */
    public void collect(String key, String value){
        priorityQueue.add(new Pair<String, String>(key, value));
        keys.add(key);
    }

    public TreeMap<String, List<String>> sortedMap(){
        TreeMap<String, List<String>> map = new TreeMap<String, List<String>>();

        for(Pair<String, String> pair : priorityQueue){
            if(map.containsKey(pair.getKey())){
                map.get(pair.getKey()).add(pair.getValue());
            }else{
                List<String> postingList = new ArrayList<String>();
                postingList.add(pair.getValue());
                map.put(pair.getKey(), postingList);
            }
        }
        return map;
    }
}
