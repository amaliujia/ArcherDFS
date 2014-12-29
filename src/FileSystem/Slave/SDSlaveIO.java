package FileSystem.Slave;

/**
 * Created by amaliujia on 14-12-29.
 */
public class SDSlaveIO {

    public byte[] read(long chunkID, long offset, long size) {
        return new byte[0];
    }

    public boolean write(long chunkID, long offset, int size, byte[] data) {
        return false;
    }

    public boolean delete(long chunkID) {
        return false;
    }
}
