package Slave;


import Heartbeats.HKSlaveHeartbeats;

import java.io.*;
import java.net.Socket;
/**
 * @author kanghuang
 */
public class HKSlaveNode implements Runnable{
    private PrintWriter pw;

    private BufferedReader bs;

    private String masterAddress;

    private int masterPort;

    private Socket socket;

    HKSlaveHeartbeats heartBeat;

   // private Logger logger = LoggerFactory.getLogger(HKSlaveNode.class);

    public HKSlaveNode(String masterAddress, int masterPort){
        this.masterAddress = masterAddress;
        this.masterPort = masterPort;
    }

    public void connect(){
        try{
            socket = new Socket(masterAddress, masterPort);
            bs = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.heartBeat = new HKSlaveHeartbeats(socket.getLocalPort());
        }
        catch(IOException ex) {
            //logger.error("MasterAddress or MasterPort is wrong");
        }
        //logger.info("Connection success");
    }

    public void disconnect(){
        try{
            bs.close();
            pw.close();
            socket.close();
        }
        catch (IOException ex)
        {
            //logger.error("disConnection error\n");
        }
       // logger.info("disConnection success");
    }

    /**
     * basic services
     * @throws IOException
     */
    public void startService() throws IOException{
        String command = bs.readLine();
        if (command == null){
           // logger.error("Interrupt!");
            System.exit(1);
        }
        if (command.equals("Alive?")){
            new Thread(this.heartBeat).start();
           // logger.info("heartBeat");
        }
    }

    /**
     *  keep working
     */
    public void run() {
        while(true){
            try {
                this.startService();
            } catch (IOException e) {
                //logger.error("socket connection error");
                e.printStackTrace();
            }

        }
    }
}
