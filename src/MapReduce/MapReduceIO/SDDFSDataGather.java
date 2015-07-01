package MapReduce.MapReduceIO;

import FileSystem.Base.SDDFSNode;
import Protocol.DFS.SlaveService.SDSlaveService;
import Util.SDUtil;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * @author amaliujia
 */
public class SDDFSDataGather {
    private SDFileSegment segment;
    private byte[] dataBuffer;
    private int count;

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
        count = 0;
    }

    public String readLine(){
        if(dataBuffer == null || dataBuffer.length == 0 || count == dataBuffer.length){
            return null;
        }

        StringBuffer stringBuffer = new StringBuffer();
        char ch;
        while ((ch = (char) dataBuffer[count++]) != '\n'){
            stringBuffer.append(ch);
        }
        String s = stringBuffer.toString();
        if(s.length() == 0){
            return null;
        }else{
            return s;
        }
    }
}
