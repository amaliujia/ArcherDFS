package Master;

import FileSystem.Index.SDDFSIndex;
import FileSystem.Master.SDMasterRMIService;
import Logging.SDLogger;
import Protocol.DFS.MasterService.SDMasterService;
import Util.SDUtil;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by amaliujia on 14-12-20.
 */
public class SDMasterNode {
    private static Logger log4jLogger = Logger.getLogger(SDMasterNode.class);


    public static ArrayList<SDSlave> slaveList;

    public static HashMap<Integer, SDSlave> slaveHashMap;

    private ExecutorService threadsPool;

    private Timer heartbeatsTimer;

    private SDDFSIndex sddfsIndex;

    private SDLogger sdLogger;

    private SDMasterService sdMasterRMIService;

    private Registry registry;

    public SDMasterNode(){
        slaveList = new ArrayList<SDSlave>();
        slaveHashMap = new HashMap<Integer, SDSlave>();
        threadsPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * SDUtil.POOLSIZE);
    }

    public void startService() throws RemoteException, UnknownHostException {
        sdLogger = new SDLogger(SDUtil.LOGPATH);

        //start dfs master service
        String serviceName = SDMasterService.class.getCanonicalName();
        sddfsIndex = new SDDFSIndex(sdLogger);
        sdMasterRMIService = new SDMasterRMIService(sddfsIndex);
        registry = LocateRegistry.createRegistry(SDUtil.MASTER_RMIRegistry_PORT);
        registry.rebind(SDMasterService.class.getCanonicalName(), sdMasterRMIService);

        log4jLogger.info("DFS Master node starts running");
    }

    /**
     * Start listening service.
     * @param port
     *          local monitoring port.
     * @throws IOException
     *          throw this exception when
     */
    public void startService(int port) throws IOException {

        //start monitoring port
        ListenerService listener = new ListenerService(port);
        listener.start();

        //read command
        BufferedReader stdReader = new BufferedReader(new InputStreamReader(System.in));
        promptPrinter("help");

        String inputString;
        String[] args;

        while(true) {
            inputString = stdReader.readLine();
            args = inputString.split(" ");

            if (args.length == 0) {
                continue;
            }
        }
    }

    /**
     * Print appropriate messages in console.
     * @param message
     *          a String, used to print different messages into console.
     */
    private void promptPrinter(String message){
        if(message.equals("help")){
            System.out.println("Instruction: Please input your command based on following format");
        }else if(message.equals("start")){
            System.out.println("Start Command: ");
            System.out.println("              ---- start slaveID someProcess inputFile outputFile");
        }

    }

    /**
     * Listener Service, run in a separate thread
     */
    private class ListenerService extends Thread{
        ServerSocket listener = null;

        public ListenerService(int port){
            try {
                listener = new ServerSocket(port);
            } catch (IOException e) {
                //System.out.println("listener socket fails");
                //logger.error("listener socket fails");
                e.printStackTrace();
            }
        }

        /**
         * run function, inherited from Thread Class
         */
        public void run() {
            while(true) {
                try {
                    Socket sock = listener.accept();
                    log4jLogger.info("Slave connected!");
                    SDSlave aSlave = new SDSlave(sock.getInetAddress(), sock.getPort());
                    aSlave.setReader(new BufferedReader(new InputStreamReader(sock.getInputStream())));
                    aSlave.setWriter(new PrintWriter(sock.getOutputStream()));
                    int key = (int)(getCurrentTimeInMillionSeconds() % 1000000);
                    synchronized (slaveHashMap){
                        //TODO: write a robust slave id assignment function
                        slaveHashMap.put(key, aSlave);
                    }
                }catch (IOException e){
                    log4jLogger.error("fail to establish a socket with a slave node");
                    e.printStackTrace();
                }
            }
        }

        private long getCurrentTimeInMillionSeconds(){
            Date currentData = new Date();
            return currentData.getTime();
        }

    }
}
