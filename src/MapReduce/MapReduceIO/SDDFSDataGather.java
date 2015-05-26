package MapReduce.MapReduceIO;

import FileSystem.Base.SDDFSNode;
import Protocol.DFS.MasterService.SDMasterService;
import Protocol.DFS.SlaveService.SDSlaveService;
import Util.SDUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by amaliujia on 15-5-25.
 */
public class SDDFSDataGather {
    private SDFileSegment segment;
    private byte[] dataBuffer;

    public SDDFSDataGather(SDFileSegment segment){
        this.segment = segment;
    }

    public void get() throws IOException, NotBoundException {
        SDDFSController controller = SDDFSController.instance();
        SDDFSDataBlock[] blocks = controller.getBlocks(segment.getFilename(), segment.getSize(), segment.getOffset());
        if(blocks == null){
            return;
        }

        //start to read data from slave servers one by one.
        ArrayList<Byte> buffer = new ArrayList<Byte>();
        for(SDDFSDataBlock block : blocks){
            //connect to slave node
            SDDFSNode node;
            byte[] data = null;
            while ((node = block.getNextNode()) != null) {
                Registry registry = LocateRegistry.getRegistry(node.getRegistryHost(), node.getRegistryPort());
                SDSlaveService service = (SDSlaveService) registry.lookup(SDSlaveService.class.getCanonicalName());
                data = service.read(block.getChunkID(), block.getOffset(), block.getSize());
                if(data != null){
                   break;
                }
            }
            if(data == null){
                throw new IOException("No data read from slave node");
            }
            for(int i = 0; i < data.length; i++){
                Byte b = data[i];
                buffer.add(b);
            }
        }
        Byte[] temp = new Byte[buffer.size()];
        buffer.toArray(temp);
        dataBuffer = SDUtil.toPrimitivesByte(temp);
    }

    public String readLine(){
        return null;
    }
}
