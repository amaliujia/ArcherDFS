package Master;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.InetAddress;

/**
 * Created by amaliujia on 14-12-13.
 */
public class SDSlave {
    private InetAddress address;
    private int port;
    public BufferedReader in;
    public PrintWriter out;
    private int slaveID;

    /**
     * Constructor for slave node
     * @param id
     *          slave identifier
     * @param address
     *          slave IP adress
     * @param port
     *          slave socket port
     */
    public SDSlave(int id, InetAddress address, int port){
        this.slaveID = id;
        this.address = address;
        this.port = port;
    }

    /**
     * Constructor for slave node
     * @param address
     *          slave identifier
     * @param port
     *          slave socket port
     */
    public SDSlave(InetAddress address, int port){
        this.address = address;
        this.port = port;
    }

    /**
     * set buffered reader for slave
     * @param reader
     *          buffered reader reference
     */
    public void setReader(BufferedReader reader){
        this.in = reader;
    }

    /**
     * set print writer for slave
     * @param writer
     *          print writer reference
     */
    public void setWriter(PrintWriter writer){
        this.out = writer;
    }

    /**
     * print a string which summary
     * @return
     *      return informational string
     */
    public String toString(){
        return "INetAddress:\t" + address + " \tport:\t" + port;
    }

    public PrintWriter getWriter(){
        return out;
    }

    public String getAddress(){
        return this.address.getHostAddress();
    }

    public String getPortString(){
        return "" + this.port;
    }


}