package RMI;

import ArcherException.SDConnectionException;
import ArcherException.SDMarshallingException;
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
            //TODO: return invocation result
        }catch (IOException e){
            throw new SDConnectionException("Fail to send invocation", e);
        }
    }

    private Object invokeReturn(Method method) throws IOException, ClassNotFoundException {
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
