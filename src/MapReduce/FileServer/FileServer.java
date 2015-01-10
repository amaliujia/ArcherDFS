package MapReduce.FileServer;

import com.sun.corba.se.spi.activation.Server;

import java.io.File;
import java.net.BindException;

/**
 * The HTTP server that handle with transforming intermediate
 * files form mappers to reducers.
 *
 * @author Jian Fang(jianf)
 * @author Fangyu Gao(fangyug)
 */
public class FileServer {
//    private int port;
//    private String fileDir;
//
//    public FileServer(int port, String fileDir){
//        this.port = port;
//        this.fileDir = fileDir;
//    }
//
//    public void start()
//            throws Exception {
//        File folder = new File(fileDir);
//
//        if(!folder.exists()){
//            folder.mkdirs();
//        }
//
//        if(!folder.isDirectory()){
//            throw new IllegalArgumentException("should set directory not a file for file server");
//        }
//
//        Server server = new Server(port);
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.setContextPath("/");
//        context.addServlet(new ServletHolder(new FileHandler(fileDir)), "/*");
//        server.setHandler(context);
//        try{
//            server.start();
//        } catch (BindException e){
//            System.err.println("bind error, address already in use");
//            System.exit(-1);
//        }
//    }
}
