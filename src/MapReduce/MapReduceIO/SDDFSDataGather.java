package MapReduce.MapReduceIO;

/**
 * Created by amaliujia on 15-5-25.
 */
public class SDDFSDataGather {
    private SDFileSegment segment;

    public SDDFSDataGather(SDFileSegment segment){
        this.segment = segment;
    }

    public String[] get(){
        SDDFSController controller = SDDFSController.instance();
        SDDFSDataBlock[] blocks = controller.getBlocks(segment.getFilename(), segment.getSize(), segment.getOffset());
        if(blocks == null){
            return null;
        }

        //start to read data from slave servers one by one.
        return null;
    }
}
