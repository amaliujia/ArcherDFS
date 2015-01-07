package MapReduce.Common;

import FileSystem.Util.SDDFSConstants;

/**
 * Created by amaliujia on 15-1-7.
 */
public abstract class SDAbstractMapReduce implements SDMapReduce {
    private static final int DEFAULT_ATTEMPT_COUNT = 3;

    private String jobName;
    private String className;
    private String inputFile;
    private String outputFile;
    private int outputFileReplica = SDDFSConstants.DEFAULT_REPLICA_NUMBER;
    private int outputFileBlockSize = SDDFSConstants.DEFAULT_LINE_COUNT;
    private int mapperAmount = 0;
    private int reducerAmount = 0;
    private int maxAttemptCount = DEFAULT_ATTEMPT_COUNT;
}
