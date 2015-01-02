package Logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by amaliujia on 14-12-26.
 */
public class SDLogger {
    // location of log file
    private String logFilePath;

    // writer for logging
    private BufferedWriter writer;

    // error log
   // private Logger logger = LoggerFactory.getLogger(SDLogger.class);

    public SDLogger(String filePath){
        try {
            writer = new BufferedWriter(new FileWriter(filePath, true));
        } catch (IOException e) {
            //logger.error("Cannot find log file");
            e.printStackTrace();
        }

        this.logFilePath = filePath;
    }

    /**
     * record operations into log file
     * @param operation
     */
    public synchronized void writeLog(SDLogOperation operation){
        try {
            writer.write(operation.toString());
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            //logger.error("Fail to write log\n\t" + operation.toString());
        }
    }


}
