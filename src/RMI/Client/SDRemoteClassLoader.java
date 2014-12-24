package RMI.Client;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by amaliujia on 14-12-24.
 */
public class SDRemoteClassLoader extends ClassLoader{
    private final int BUFFERSIZE = 4096;

    //host name of server
    private String address;

    //port number of stub downloading service
    private int port;

    //class name
    private String className;

    public SDRemoteClassLoader(String address, int port, String className){
        this.address = address;
        this.port = port;
        this.className = className;
    }

    public Class getStubtClass() throws IOException {
        Class c;
        try{
           c = Class.forName(SDRMIClientUtil.STUB_Prefix + className + SDRMIClientUtil.STUB_SUFFIX);
        } catch (ClassNotFoundException e) {
           c = getRemoteStubClass();
        }
        return c;
    }

    private Class getRemoteStubClass()
            throws IOException{
        byte[] stubClassBytes = getClassFileByte(SDRMIClientUtil.STUB_Prefix + className + SDRMIClientUtil.STUB_SUFFIX);
        return defineClass(SDRMIClientUtil.STUB_Prefix + className + SDRMIClientUtil.STUB_SUFFIX, stubClassBytes, 0, stubClassBytes.length);
    }

    /**
     * Using class file URL to get class file from server.
     *
     * @param className the name of class
     * @return byte[], byte file data
     * @throws IOException
     */
    private byte[] getClassFileByte(String className)
            throws IOException {
        String classPath = className.replaceAll("\\.", "/") + ".class";
        String urlStr = "http://" + address + ":" + port + "/" + classPath;
        URL url = new URL(urlStr);
        BufferedInputStream in = new BufferedInputStream(url.openStream());
        byte buffer[] = new byte[BUFFERSIZE];
        int length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while((length = in.read(buffer, 0, BUFFERSIZE)) != -1){
            out.write(buffer, 0, length);
        }
        byte[] classFileBytes = out.toByteArray();
        in.close();
        out.close();
        return classFileBytes;
    }

}
