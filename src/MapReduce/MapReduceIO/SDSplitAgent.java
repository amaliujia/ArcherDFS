package MapReduce.MapReduceIO;

import java.rmi.RemoteException;
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
        long[] lines = null;
        try {
            lines = controller.splitLines(DFSfile);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (lines == null){
            return results;
        }

        //TODO: Bug here. what if num of lines is less than num?
        int splitSize = lines.length  / num;
        for(int i = 0; i < num; i++){
                long o = lines[i * splitSize];
            if(i == (num - 1)){
                results.add(new SDFileSegment(DFSfile, (lines.length - i * splitSize), o));
            }else{
                results.add(new SDFileSegment(DFSfile, splitSize, o));
            }
        }
        return results;
    }



}
