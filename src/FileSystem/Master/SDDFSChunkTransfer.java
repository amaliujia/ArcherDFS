package FileSystem.Master;

import FileSystem.Base.SDDFSFile;

/**
 * Created by amaliujia on 15-1-4.
 */
public class SDDFSChunkTransfer {

    private SDDFSFile file;

    public SDDFSChunkTransfer(SDDFSFile sddfsFile){
        file = sddfsFile;
    }

    private void distributeChunks(){
//        Iterator<SDDFSNode> iterator = chunkNodes.iterator();
//        while(iterator.hasNext()){
//            SDDFSNode node = iterator.next();
//            try {
//                Registry registry = LocateRegistry.getRegistry(node.getRegistryHost(), node.getRegistryPort());
//                SDSlaveService slaveService = (SDSlaveService)registry.lookup(node.getServiceName());
//                //slaveService.write(id, offset, size, )
//            } catch (RemoteException e) {
//                System.err.println("Cannot get registry from " + node.toString() );
//                continue;
//            } catch (NotBoundException e) {
//                System.err.println("Cannot look up service " + node.getServiceName() +  " from " + node.toString());
//                continue;
//            }
//        }
    }

}
