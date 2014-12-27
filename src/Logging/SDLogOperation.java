package Logging;

import java.util.Arrays;
import java.util.List;

/**
 * Created by amaliujia on 14-12-26.
 */
public class SDLogOperation {
    private int type;

    private List<Object> argmuents;

    public SDLogOperation(int type){
        this.type = type;
    }

    public SDLogOperation(int type, Object[] argmuents){
        this.argmuents = Arrays.asList(argmuents);
        this.type = type;
    }

    public int getType(){
        return type;
    }

    public List<Object> getArgmuents(){
        return argmuents;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();

        s.append(this.type);

        for(int i = 0; i < argmuents.size(); i++){
            s.append(" " + argmuents.get(i));
        }

        return s.toString();
    }


}
