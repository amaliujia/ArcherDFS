package RMI;

import ArcherException.SDConnectionException;
import ArcherException.SDUnmarshallingException;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by amaliujia on 14-12-23.
 */
public class SDInvokeRemoteConnection extends SDRemoteConnection {
    public SDInvokeRemoteConnection(String address, int port) throws SDConnectionException {
        super(address, port);
    }

    public void invoke(SDRemoteObjectReference ref, Method method, long methodHash, Object[] params)
            throws SDConnectionException{
        try{
            Class<?>[] types = method.getParameterTypes();
            out.writeByte(SDCommandConstants.INVOKE);
            out.writeObject(ref);
            out.writeLong(methodHash);
            //TODO: marvalue and send parameters
        }catch (IOException e){
            throw new SDConnectionException("Fail to send invocation", e);
        }
    }

    private Object invokeReturn(Method method) throws IOException{
        if(in.readByte() != SDCommandConstants.RETURN){
           throw new SDUnmarshallingException("Invoke return command code is invalid");
        }
        switch (in.readByte()){
            case SDCommandConstants.RETURN:
                break;
            default:
                break;
        }
        Class<?> returnType = method.getReturnType();
        if(returnType == void.class){
            return null;
        }

        //TODO unmarshalling
        return null;
    }
}
