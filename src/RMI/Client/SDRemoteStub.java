package RMI.Client;

import ArcherException.SDConnectionException;
import ArcherException.SDRemoteReferenceObjectException;

import java.io.Serializable;
import java.lang.reflect.Method;


/**
 * Created by amaliujia on 14-12-24.
 */
public abstract class SDRemoteStub implements Serializable{

    // Remote of remote object
    protected SDRemoteObjectReference ref;

    public SDRemoteStub(SDRemoteObjectReference ref){
        this.ref = ref;
    }

    public void setRemoteRef(SDRemoteObjectReference ref){
        this.ref = ref;
    }

    protected Object invoke(Method method, Object[] params) throws SDRemoteReferenceObjectException {
        if(ref == null){
            throw new SDRemoteReferenceObjectException("RemoteRef doesn't exit in Stub");
        }else{
            try {
                return ref.invoke(method, 0, params);
            } catch (SDConnectionException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



}
