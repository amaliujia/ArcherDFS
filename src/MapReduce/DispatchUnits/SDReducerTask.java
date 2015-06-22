package MapReduce.DispatchUnits;

/**
 * @author amaliujia
 */
public class SDReducerTask extends SDTask {

    private String outputFilePrefix;

    private int numShards;

    public SDReducerTask(int id){
        jobID = id;
    }

    public String getOutputFilePrefix(){
        return this.outputFilePrefix;
    }

    public int getNumShards(){
        return this.numShards;
    }

    public void setNumShards(int n){
        this.numShards = n;
    }

    public void SetOutputFilePrefix(String outputFilePrefix){
        this.outputFilePrefix = outputFilePrefix;
    }

}
