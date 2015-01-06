package FileSystem.Master;

import FileSystem.Base.SDDFSFile;
import FileSystem.Base.SDDFSNode;
import FileSystem.Base.SDFileChunk;
import FileSystem.Slave.SDSlaveRMIService;
import Protocol.DFS.SlaveService.SDSlaveService;
import Util.SDUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by amaliujia on 15-1-4.
 */
public class SDDFSChunkTransfer {

    private SDDFSFile file;

    private RandomAccessFile randomAccessFile;

    public SDDFSChunkTransfer(SDDFSFile sddfsFile){
        file = sddfsFile;
    }

    public void distributeFile(){
        getAccessToFile();
        if(randomAccessFile == null){
            return;
        }

        for(int i = 0; i < file.getChunks().length; i++){
            System.err.println("Chunk transfer !!  length " + file.getChunks().length + " i: " + i);
            SDFileChunk chunk = file.getChunks()[i];
            Set<SDDFSNode> chunkNodes = chunk.getChunkNodes();
            Iterator<SDDFSNode> iterator = chunkNodes.iterator();
            while(iterator.hasNext()){
                SDDFSNode node = iterator.next();
                try {
                    Registry registry = LocateRegistry.getRegistry(node.getRegistryHost(), node.getRegistryPort());
                    SDSlaveService slaveService = (SDSlaveService)registry.lookup(SDSlaveService.class.getCanonicalName());
                    System.err.println("data chunk length: " + getChunkData(chunk).length);
                    slaveService.write(chunk.getId(), chunk.getOffset(), chunk.getSize(), getChunkData(chunk));
                } catch (RemoteException e) {
                    System.err.println("Cannot get registry from " + node.toString() );
                    continue;
                } catch (NotBoundException e) {
                    System.err.println("Cannot look up service " + node.getServiceName() +  " from " + node.toString());
                    continue;
                } catch (IOException e) {
                    System.err.println("IO exception when try to distributed chunk " +
                                        chunk.toString() + " to " + node.toString());
                    continue;
                }
            }
        }
    }

    private void getAccessToFile(){
        try {
            randomAccessFile = new RandomAccessFile(file.getFileName(), "r");
        } catch (FileNotFoundException e) {
            System.err.println(file.getFileName() + " doesn't exist" );
            return;
        }
    }

    private byte[] getChunkData(SDFileChunk chunk){
        long offset = chunk.getOffset();
        int size = chunk.getSize();
        byte[] bytes = new byte[size];

        try {
            randomAccessFile.seek(offset);
            int len = randomAccessFile.read(bytes, 0, size);
            if(len != size){
                SDUtil.fatalError("Read incomplete data from files " + file.toString() + " in which chunk " + chunk.toString()
                                + " have problem");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
