package MapReduce.MapReduceIO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amaliujia on 15-5-12.
 */
public class SDSplitAgent {
    private String host;
    private int port;

    public SDSplitAgent(String address, int p){
        host = address;
        port = p;
    }

    public SDSplitAgent(){

    }

    public List<SDFileSegment> split(String DFSfile, int num){
        ArrayList<SDFileSegment> results = new ArrayList<SDFileSegment>();

        //Get RMI controller for DFS.
        SDDFSController controller = SDDFSController.instance();


        return results;
    }



}
