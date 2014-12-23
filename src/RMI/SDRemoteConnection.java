package RMI;


import ArcherException.SDConnectionException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by amaliujia on 14-12-23.
 */
public abstract class SDRemoteConnection {

    protected Socket socket;

    protected ObjectInputStream in;

    protected ObjectOutputStream out;

    public SDRemoteConnection(String address, int port) throws SDConnectionException {
        try {
            socket = new Socket(address, port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new SDConnectionException("Failed to create remote connection ", e);
        }
    }

    public SDRemoteConnection(Socket socket) throws SDConnectionException {
        try {
            this.socket = socket;
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new SDConnectionException("Failed to create remote connection ", e);
        }
    }

    public void close() throws SDConnectionException {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            throw new SDConnectionException("Failed to close connection", e);
        }
    }

    public void marshalling(Class<?> type, Object value, ObjectOutputStream out){

    }

    public Object unmarshalling(Class<?> type, ObjectInputStream in){

        return null;
    }

}
