package Master;

import Util.SDUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by amaliujia on 14-12-20.
 */
public class SDMasterNode {
    public static ArrayList<SDSlave> slaveList;

    public SDMasterNode(){
        slaveList = new ArrayList<SDSlave>();
    }

    public void startService(){
        ListenerService listener = new ListenerService(SDUtil.masterLinstenerPort);
        listener.start();
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
            System.out.println("              <processName> [arg1] [arg2]....[argN]");
            System.out.println("              ps (prints a list of local running processes and their arguments)");
            System.out.println("              quit (exits the ProcessManager)");
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
                System.out.println("listener socket fails");
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
                    System.out.println("New slave connected");
                    SDSlave aSlave = new SDSlave(sock.getInetAddress(), sock.getPort());
                    aSlave.setReader(new BufferedReader(new InputStreamReader(sock.getInputStream())));
                    aSlave.setWriter(new PrintWriter(sock.getOutputStream()));
                    synchronized (slaveList){
                        slaveList.add(aSlave);
                    }
                }catch (IOException e){
                    System.err.println("fail to establish a socket with a slave node");
                    e.printStackTrace();
                }
            }
        }

    }
}
