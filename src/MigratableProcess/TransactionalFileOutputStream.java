package MigratableProcess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/**
 * Created by amaliujia on 14-12-14.
 */
public class TransactionalFileOutputStream extends OutputStream implements Serializable {
    private String fileName;

    private transient RandomAccessFile randomAccessFile;

    private long offset;

    private boolean migratable;

    private Logger logger = LoggerFactory.getLogger(TransactionalFileOutputStream.class);

    public TransactionalFileOutputStream(String arg, boolean b){
         this.fileName = arg;
         try{
             this.randomAccessFile = new RandomAccessFile(this.fileName, "rws");
         } catch (FileNotFoundException e) {
             logger.error("Cannot find file " + this.fileName + " in file system");
         } catch (IllegalArgumentException e){
             logger.error("illegal arguments");
         } catch (SecurityException e){
             logger.error("Security problem");
         }

         offset = 0L;
         migratable = false;

    }


    public void write(int b) throws IOException {
        if(migratable || this.randomAccessFile == null){
            this.randomAccessFile = new RandomAccessFile(this.fileName, "rws");
            migratable = false;
        }


        System.out.println(offset);
        this.randomAccessFile.seek(offset);
        offset++;
        try {
            //System.out.println("************");
            this.randomAccessFile.write(b);
        }catch (IOException e){
            logger.error("Fail to write to " + this.fileName);
            e.printStackTrace();
        }
    }

    public void closeFileStream(){

        try {
            this.randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isMigaratable(){
        if(migratable){
            return true;
        }
        return false;
    }

    public void setMigaratable(boolean flag){
        this.migratable = flag;
    }

    public long getOffset(){
        return offset;
    }
}
