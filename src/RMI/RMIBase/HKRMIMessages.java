package RMI.RMIBase;

import RMI.Client.SDRemoteObjectReference;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by kanghuang on 12/24/14.
 */
public class HKRMIMessages {
    public enum RMIMsgType {
        LOOKUP,
        LIST,
        CALL
    };
    private RMIMsgType type;
    private Object[] rorContent;

    public  HKRMIMessages(Object[] ror, RMIMsgType type){
        this.rorContent = ror;
        this.type = type;
    }

    public SDRemoteObjectReference getRorContent() {
        if (type != RMIMsgType.CALL) {
            return null;
        }
        //return (SDRemoteObjectReference)(((Object[]) rorContent)[0]);
        return (SDRemoteObjectReference)rorContent[0];
    }

    public  String getServiceName(){
        if (type != RMIMsgType.CALL) {
            return null;
        }
        //return (String)(((Object[]) rorContent)[1]);
        return (String) rorContent[1];
    }

    public Object[] getArguments(){
        if (type != RMIMsgType.CALL) {
            return null;
        }
      //  Object[] args = Arrays.copyOfRange((Object[])rorContent, 2, ((Object[])rorContent).length);
        Object[] args = Arrays.copyOfRange(rorContent, 2, rorContent.length);
        return args;
    }
}
