package RMI;

import ArcherException.SDConnectionException;
import ArcherException.SDMarshallingException;
import ArcherException.SDUnmarshallingException;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * SDInvokeRemoteConnection is a subclass of SDRemoteConnection.
 * It is mainly used to send a invocation from client to server.
 * This connection will handle invoke, marshalling, unmarshalling.
 * It may support features like timeout, at-least one send, etc.
 */
public class SDInvokeRemoteConnection extends SDRemoteConnection {
    public SDInvokeRemoteConnection(String address, int port) throws SDConnectionException {
        super(address, port);
    }

    /**
     * Start point of an Invocation. Based on protocol, send method and parameters to server, and
     * waiting return.
     * @param ref
     * @param method
     * @param methodHash
     * @param params
     * @throws SDConnectionException
     */
    public Object invoke(SDRemoteObjectReference ref, Method method, long methodHash, Object[] params)
            throws SDConnectionException, ClassNotFoundException {
        try{
            Class<?>[] types = method.getParameterTypes();
            out.writeByte(SDCommandConstants.INVOKE);
            out.writeObject(ref);
            out.writeLong(methodHash);
            try {
                if(params != null){
                    for(int i = 0; i < params.length; i++){
                        marshalling(types[i], params[i], out);
                    }
                }
            } catch (IOException e){
                throw new SDMarshallingException("Failed to marshalling", e);
            }
            out.flush();
            return invokeReturn(method);
        }catch (IOException e){
            throw new SDConnectionException("Fail to send invocation", e);
        }
    }

    /**
     * Call in invoke function, waiting for receiving RMI result.
     * @param method
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private Object invokeReturn(Method method) throws IOException, ClassNotFoundException {
        //TODO: define return message format
        if(in.readByte() != SDCommandConstants.RETURN){
           throw new SDUnmarshallingException("Invoke return command code is invalid");
        }
        switch (in.readByte()){
            case SDCommandConstants.RETURN:
                break;
            default:
                break;
        }
        //TODO: handle exception return and unexpected return
        Class<?> returnType = method.getReturnType();
        if(returnType == void.class){
            return null;
        }

        return unmarshalling(returnType, in);
    }
}
