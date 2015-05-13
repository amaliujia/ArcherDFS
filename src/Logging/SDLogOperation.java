package Logging;

import java.util.Arrays;
import java.util.List;

/**
 * @author amaliujia
 * @author kanghuang
 */
public class SDLogOperation {

    public static final byte UNKNOWN = 0x0;

    public static final byte UPDATE_DATA_NODE = 0x1;

    public static final byte REMOVE_DATA_NODE = 0x2;

    public static final byte DFS_CREATE_FILE = 0x3;

    public static final byte DFS_CREATE_CHUNK = 0x4;

    public static final byte DFS_DELETE_FILE = 0x5;

    public static final byte DFS_DISTRIBUTE_FILE = 0x6;

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
