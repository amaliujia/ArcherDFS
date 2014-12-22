package MigratableProcess;

/**
 * Created by kanghuang on 12/16/14.
 */

public class SDProcessInfo {

    public MigratableProcesses process = null;
    public SDProcessStatus status = null;

    public SDProcessInfo(SDProcessStatus status, MigratableProcesses process){
        this.status = status;
        this.process = process;
    }

}
