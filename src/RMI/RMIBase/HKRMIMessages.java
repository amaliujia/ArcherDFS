package RMI.RMIBase;

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
    private Object rorContent;

    public Object getRorContent() {
        if (type != RMIMsgType.CALL) {
            return null;
        }
        return rorContent;
    }
}
