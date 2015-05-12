package FileSystem.Slave;

import FileSystem.Util.SDDFSConstants;
import Util.SDUtil;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by amaliujia on 14-12-29.
 */
public class SDSlaveIO {
    public static Logger Log4jLogger = Logger.getLogger(SDSlaveIO.class);

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

    public long[] offsetOfLinesInChunk(long chunkID) {
        File file = new File(getChunkPath(chunkID));
        Long[] results = null;
        try {
            List<Long> offsets = new ArrayList<Long>();
            FileInputStream in = new FileInputStream(file);
            byte ch;
            byte prevChar = -1;
            long offset = 0;
            while ((ch = (byte) in.read()) != -1) {
                if (prevChar == -1 || prevChar == '\n') {
                    offsets.add(offset);
                }
                offset++;
                prevChar = ch;
            }
            results = new Long[offsets.size()];
            offsets.toArray(results);
        } catch (FileNotFoundException e) {
            Log4jLogger.error(SDUtil.LOG4JERROR_DFS + "chunk " + chunkID + "doesn't exist in ");
            return null;
        } catch (IOException e) {
            Log4jLogger.error(SDUtil.LOG4JERROR_DFS + getChunkPath(chunkID) + " cannot be write");
            return null;
        }
        return results != null ? SDUtil.toPrimitives(results) : null;
    }
}
