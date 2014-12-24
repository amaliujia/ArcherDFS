package RMI.Client;


/**
 * Created by amaliujia on 14-12-24.
 */
public class SDRemoteClassLoader extends ClassLoader{
    //host name of server
    String address;

    //port number of stub downloading service
    int port;

    //class name
    String className;

    public SDRemoteClassLoader(String address, int port, String className){
        this.address = address;
        this.port = port;
        this.className = className;
    }

    public Class getStubtClass(){
        Class c;
        try{
           c = Class.forName(className + SDRMIClientUtil.STUB_SUFFIX);
        } catch (ClassNotFoundException e) {
           c = getRemoteSubClass();
        }
        return c;
    }


    private Class getRemoteSubClass(){

        return null;
    }

}
