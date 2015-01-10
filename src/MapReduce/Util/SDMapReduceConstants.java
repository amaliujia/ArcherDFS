package MapReduce.Util;

/**
 * Created by amaliujia on 15-1-9.
 */
public class SDMapReduceConstants {
    public static final long DEFAULT_HEARTBEAT_PERIOD = 1000;
    public static final long DEFAULT_HEARTBEAT_INVALID = 3 * DEFAULT_HEARTBEAT_PERIOD;
    public static final String MAPREDUCE_DELIMITER_REGEX = "\\s+";
    public static final String MAPREDUCE_DELIMITER = "\t";

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String CLASS_CONTENT_TYPE = "application/x-java-class";
    public static final String CLASS_FILE_URI = "classes";
    public static final String TASKS_FILE_URI = "tasks";
    public static final String TASK_FOLDER_PREFIX = "simplemr_task_";
    public static final String DEFAULT_OUTPUT_DIR = TASK_FOLDER_PREFIX + "output/";
}
