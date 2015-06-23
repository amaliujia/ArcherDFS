package MapReduce.DispatchUnits;

/**
 * @author amaliujia
 */
public class SDReducerTask extends SDTask {

    private String outputFilePrefix;

    private int numShards;

    private int numMappers;

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

    public void setNumMappers(int n){
        this.numMappers = n;
    }

    public int getNumMappers(){
        return this.numMappers;
    }

    public void SetOutputFilePrefix(String outputFilePrefix){
        this.outputFilePrefix = outputFilePrefix;
    }

}
