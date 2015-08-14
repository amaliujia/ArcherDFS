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
    /**
     * Entrance for execute user-defined map reduce.
     * @param args
     *         args[0] jobName
     *         args[1] user-defined class name
     *         args[2] user inputfile
     *         args[3] uesr outputfile
     *         args[4] mapper number
     *         args[5] reducer number
     */
    public static void main(String[] args) {
        org.apache.log4j.BasicConfigurator.configure();
        new WordCount().run(args);
    }
}
