package FileSystem.Slave;

import FileSystem.Util.SDDFSConstants;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * Created by amaliujia on 14-12-29.
 */
public class SDSlaveIO {

    private int chunkNumber;

    public byte[] read(long chunkID, long offset, int size) throws IOException {
        File file = new File(getChunkPath(chunkID));
        if(file.exists()){
            RandomAccessFile rFile = new RandomAccessFile(file, "r");
            rFile.seek(offset);
            byte[] readBuffer = new byte[size];
            int len = rFile.read(readBuffer, 0, size);
            if(len > 0){
                byte[] data = Arrays.copyOf(readBuffer, len);
                rFile.close();
                return data;
            }else{
                return null;
            }
        }
        return null;
    }

    public boolean append(long chunkID, int size, byte[] data){
        File file = new File(getChunkPath(chunkID));
        RandomAccessFile rFile = null;
        try {
            rFile = new RandomAccessFile(file, "rw");
            rFile.seek(0);
            rFile.write(data, 0, Math.min(size, data.length));
            rFile.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean write(long chunkID, long offset, int size, byte[] data) throws IOException {
        File file = new File(getChunkPath(chunkID));
        if(file.exists()){
            RandomAccessFile rFile = new RandomAccessFile(file, "w");
            rFile.seek(offset);
            rFile.write(data, 0, (int)Math.min(size, data.length));
            rFile.close();
            return true;
        }
        return false;
    }

    public boolean delete(long chunkID) {
        File file = new File(getChunkPath(chunkID));
        if(file.exists()){
            file.delete();
            return true;
        }
        return false;
    }


    private String getChunkPath(long chunkID){
        return SDDFSConstants.DATA_DIR + System.getProperty("file.separator") + SDDFSConstants.CHUNK_PREFIX +
                chunkID + '.' + SDDFSConstants.CHUNK_SUFFIX;
    }

    public int getChunkNumber(){
        return chunkNumber;
    }
}
