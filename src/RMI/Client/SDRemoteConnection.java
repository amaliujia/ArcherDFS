package RMI.Client;


import ArcherException.SDConnectionException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by amaliujia on 14-12-23.
 */
public abstract class SDRemoteConnection {
    // TCP connection to do RMI
    protected Socket socket;

    // Connection output stream
    protected ObjectInputStream in;

    // Connection input stream
    protected ObjectOutputStream out;

    /**
     * Constructor of SDRemoteConnection, parameters are used
     * to create socket connection with server.
     * @param address
     * @param port
     * @throws SDConnectionException
     */
    public SDRemoteConnection(String address, int port) throws SDConnectionException {
        try {
            socket = new Socket(address, port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new SDConnectionException("Failed to create remote connection ", e);
        }
    }

    /**
     * Constructor of SDRemoteConnection
     * @param socket
     * @throws SDConnectionException
     */
    public SDRemoteConnection(Socket socket) throws SDConnectionException {
        try {
            this.socket = socket;
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new SDConnectionException("Failed to create remote connection ", e);
        }
    }

    /**
     * Close function to close current connection
     * @throws SDConnectionException
     */
    public void close() throws SDConnectionException {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            throw new SDConnectionException("Failed to close connection", e);
        }
    }

    /**
     * Marshalling function, used to send parameters for one RMI
     * @param type
     * @param value
     * @param out
     * @throws IOException
     */
    public void marshalling(Class<?> type, Object value, ObjectOutputStream out) throws IOException {
        if(type.isPrimitive()){
            if(type == int.class){
                out.writeInt(((Integer)value).intValue());
            }else if(type == boolean.class){
                out.writeBoolean(((Boolean)value).booleanValue());
            }else if(type == byte.class){
                out.writeByte(((Byte)value).byteValue());
            }else if(type == char.class){
                out.writeChar(((Character)value).charValue());
            }else if(type == short.class){
                out.writeShort(((Short)value).shortValue());
            }else if(type == long.class){
                out.writeLong(((Long)value).longValue());
            }else if(type == float.class){
                out.writeFloat(((Float)value).floatValue());
            }else if(type == double.class){
                out.writeDouble(((Double)value).doubleValue());
            }else{
                throw new IOException("Unknown primitive type");
            }
        }else{
            out.writeObject(value);
        }
    }

    /**
     * Unmarshalling function, used ot extract return result of one RMIß
     * @param type
     * @param in
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object unmarshalling(Class<?> type, ObjectInputStream in) throws IOException, ClassNotFoundException {
        if(type.isPrimitive()){
            if(type == int.class){
                 return Integer.valueOf(in.readInt());
            }else if(type == boolean.class){
                 return Boolean.valueOf(in.readBoolean());
            }else if(type == byte.class){
                 return Byte.valueOf(in.readByte());
            }else if(type == char.class){
                 return Character.valueOf(in.readChar());
            }else if(type == short.class){
                 return Short.valueOf(in.readShort());
            }else if(type == long.class){
                 return Long.valueOf(in.readLong());
            }else if(type == float.class){
                 return Float.valueOf(in.readFloat());
            }else if(type == double.class){
                 return Double.valueOf(in.readDouble());
            }else{
                throw new IOException("Unknown primitive type");
            }
        }else{
            return in.readObject();
        }
    }

}
