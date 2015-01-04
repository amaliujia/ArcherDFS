package MigratableProcess;

import java.io.*;

/**
 * Created by amaliujia on 14-12-14.
 */
public class TransactionalFileInputStream extends InputStream implements Serializable {
    private static final long serialVersionUID = 568680122;

    private String fileName;

    private transient RandomAccessFile randomAccessFile;

    private long offset;

    private boolean migratable;

    public TransactionalFileInputStream(String arg){
        this.fileName = arg;

        try{
            this.randomAccessFile = new RandomAccessFile(this.fileName, "rws");
        } catch (FileNotFoundException e) {
            //logger.error("Cannot find file " + this.fileName + " in file system");
            e.printStackTrace();
        } catch (IllegalArgumentException e){
           // logger.error("Illegal arguments");
            e.printStackTrace();
        } catch (SecurityException e){
           // logger.error("Security problem");
            e.printStackTrace();
        }

        offset = 0L;
        migratable = false;
    }


    public int read() throws IOException {
        if(migratable || this.randomAccessFile == null ){
            System.out.println("Read" + this.fileName);
            this.randomAccessFile = new RandomAccessFile(this.fileName, "r");
            migratable = false;
            offset -= 2000;
            System.out.println(offset);
        }
        int readBytes;
        this.randomAccessFile.seek(offset);
        readBytes = this.randomAccessFile.read();
        if(readBytes != -1){
           offset++;
        }
        return readBytes;
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
