package MapReduce.Common;

import javafx.util.Pair;

import java.util.*;

/**
 * Created by amaliujia on 15-1-7.
 */
public class SDOutputCollector {
    private PriorityQueue<Pair<String, String>> collection;
    private Set<String> keys;

    public SDOutputCollector(){
        collection = new PriorityQueue<Pair<String, String>>(10, new Comparator<Pair<String, String>>() {
            @Override
            public int compare(Pair<String, String> o1, Pair<String, String> o2) {
                if (o1.getKey().compareTo(o2.getKey()) == 0) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                   return o1.getKey().compareTo(o2.getKey());
                }
            }
        });

        //TODO: why tree set
        keys = new HashSet<String>();
    }

    public void collect(String key, String value){
        collection.add(new Pair<String, String>(key, value));
        keys.add(key);
    }

    /**
     * Get iterator of PriorityQueue.
     * @return
     *          Iterator of PriorityQueue.
     */
    public Iterator<Pair<String, String>> getIterator(){
        return collection.iterator();
    }

    /**
     * How map keys in the collections
     * @return
     */
    public int getKeyCount(){
        return keys.size();
    }

    /**
     *
     * @return
     */
    public HashMap<String, List<String>> getMap(){
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        for(Pair<String, String> pair : collection){
            if(map.containsKey(pair.getKey())){
                map.get(pair.getKey()).add(pair.getValue());
            }else{
                List<String> values = new ArrayList<String>();
                values.add(pair.getValue());
                map.put(pair.getKey(), values);
            }
        }
        return map;
    }
}
