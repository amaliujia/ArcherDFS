package FileSystem.Master;

import FileSystem.Base.SDDFSFile;
import FileSystem.Base.SDDFSNode;
import Protocol.SlaveService.SDSlaveService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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
            Set<SDDFSNode> chunkNodes = file.getChunks()[i].getChunkNodes();
            Iterator<SDDFSNode> iterator = chunkNodes.iterator();
            while(iterator.hasNext()){
                SDDFSNode node = iterator.next();
                try {
                    Registry registry = LocateRegistry.getRegistry(node.getRegistryHost(), node.getRegistryPort());
                    SDSlaveService slaveService = (SDSlaveService)registry.lookup(node.getServiceName());
                //slaveService.write(id, offset, size, )
                } catch (RemoteException e) {
                    System.err.println("Cannot get registry from " + node.toString() );
                    continue;
                } catch (NotBoundException e) {
                    System.err.println("Cannot look up service " + node.getServiceName() +  " from " + node.toString());
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
}
