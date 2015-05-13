package MigratableProcess;

import java.io.Serializable;

/**
 * @author amaliujia
 * @author kanghuang
 */
public interface MigratableProcesses extends Runnable, Serializable{

    // file operation
    public abstract void run();

    // suspending this function
    public abstract void suspend();

    // check if this process finished
    public boolean finished();

    public abstract void resume();

    public abstract void finish();
  //  public void set_migrate();

}
