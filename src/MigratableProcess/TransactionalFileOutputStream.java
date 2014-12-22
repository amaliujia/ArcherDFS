package MigratableProcess;

import java.io.*;


/**
 * Created by amaliujia on 14-12-14.
 */
public class TransactionalFileOutputStream extends OutputStream implements Serializable {
    private String fileName;
    private transient RandomAccessFile randomAccessFile;
    private long offset;
    private boolean migratable;

    public TransactionalFileOutputStream(String arg, boolean b){
         this.fileName = arg;
         try{
             this.randomAccessFile = new RandomAccessFile(this.fileName, "rws");
         } catch (FileNotFoundException e) {
             System.err.println("Cannot find file " + this.fileName + " in file system");
         } catch (IllegalArgumentException e){
             System.err.println("illegal arguments");
         } catch (SecurityException e){
             System.err.println("Security problem");
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
            System.err.println("Fail to write to " + this.fileName);
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
