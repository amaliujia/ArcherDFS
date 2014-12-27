package RMI.RMIBase;

import RMI.Client.SDRemoteObjectReference;

import java.util.Arrays;

/**
 * Created by kanghuang on 12/24/14.
 */
public class HKRMIMessage {
    public enum RMIMsgType {
        LOOKUP,
        LIST,
        CALL,
        RETURN
    };
    private RMIMsgType type;
    private Object rorContent;

    public  HKRMIMessage(Object ror, RMIMsgType type){
        this.rorContent = ror;
        this.type = type;
    }

    private boolean isTypeCall(){
        return this.type == RMIMsgType.CALL;
    }
    public RMIMsgType getType(){
        return this.type;
    }

    public Object getRawContent() {
        if (!isTypeCall()) {
            return null;
        }
        else {
            return ((Object[]) rorContent)[0];
            //return rorContent[0];
        }
    }
    public SDRemoteObjectReference getRorContent() {
        if (!isTypeCall()) {
            return null;
        }
        else {
            return (SDRemoteObjectReference)(((Object[]) rorContent)[0]);
            //return (SDRemoteObjectReference) rorContent[0];
        }
    }

    public String getServiceName(){
        if (!isTypeCall()) {
            return null;
        }
        return ((SDRemoteObjectReference)(((Object[]) rorContent)[0])).getClassName() +  (String)(((Object[]) rorContent)[1]);
       // return (String) rorContent[1];
    }

    public String methodName(){
        if (!isTypeCall()) {
            return null;
        }
        return (String)(((Object[]) rorContent)[1]);
        // return (String) rorContent[1];
    }

    public Object[] getArguments(){
        if (!isTypeCall()) {
            return null;
        }
        Object[] args = Arrays.copyOfRange((Object[])rorContent, 2, ((Object[])rorContent).length);
      //  Object[] args = Arrays.copyOfRange(rorContent, 2, rorContent.length);
        return args;
    }
}
