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
     *
     * @param id
     * @param address
     * @param port
     */
    public SDSlave(int id, InetAddress address, int port){
        this.slaveID = id;
        this.address = address;
        this.port = port;
    }

    /**
     *
     * @param address
     * @param port
     */
    public SDSlave(InetAddress address, int port){
        this.address = address;
        this.port = port;
    }

    /**
     *
     * @param reader
     */
    public void setReader(BufferedReader reader){
        this.in = reader;
    }

    /**
     *
     * @param writer
     */
    public void setWriter(PrintWriter writer){
        this.out = writer;
    }

    /**
     *
     * @return
     */
    public String toString(){
        return "INetAddress:\t" + address + " \tport:\t" + port;
    }

    public PrintWriter getWriter(){
        return out;
    }

}