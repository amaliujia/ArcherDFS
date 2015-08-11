package Examples;

import MapReduce.Abstraction.SDMapReduce;
import MapReduce.Abstraction.SDOutputCollector;

import java.util.Iterator;

/**
 * @author amaliujia
 */

public class WordCount extends SDMapReduce {

    @Override
    public void map(String value, SDOutputCollector output) {
        String[] words = value.split("\\s+");
        for(String word : words){
            output.collect(word, "1");
        }
    }

    @Override
    public void reduce(String key, Iterator values, SDOutputCollector output) {
        int count = 0;
        while(values.hasNext()){
            count++;
            values.next();
        }
        output.collect(key, String.valueOf(count));
    }
    public static void main(String[] args) {
        new WordCount().run(args);
    }
}
