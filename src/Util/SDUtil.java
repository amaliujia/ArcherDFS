package Util;

import static java.lang.System.exit;

/**
 * Created by amaliujia on 14-12-20.
 */
public class SDUtil {
    public static final int masterLinstenerPort = 16640;
    public static final String masterAddress = "128.237.191.211";
    public static final int heatbeatsPort = 16641;
    public static final long heartbeatsIntervalMillionSeconds = 3500;

    public static final String REMOTESTUBIP = masterAddress;
    public static final int RMIRegistryPort = 16642;

    public static final int POOLSIZE = 10;


    static public void fatalError (String message) {
        System.err.println (message);
        exit(1);
    }


}
