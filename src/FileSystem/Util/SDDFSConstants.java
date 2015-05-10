package FileSystem.Util;

/**
 * Created by amaliujia on 14-12-29.
 */
public class SDDFSConstants {
    public static final String CHUNK_PREFIX = "ArcherDFSChunk";
    public static final String CHUNK_SUFFIX = "ar";
    public static final String DATA_DIR = "/Users/amaliujia/Documents/github/ArcherDFS/chunks";

    public static final int DEFAULT_REPLICA_NUMBER = 3;
    public static final int DEFAULT_BLOCK_SIZE = (1 << 20);
    public static final int DEFAULT_LINE_COUNT = 40000;
    public static final int DEFAULT_ATTEMPT_COUNT = 3;

    public static final int DEFAULT_SLAVE_REGISTRY_PORT = 16644;

    public static final long CHUNK_SIZE = 0x4000000;
}
