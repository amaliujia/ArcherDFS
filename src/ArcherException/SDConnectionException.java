package ArcherException;


import java.io.IOException;

/**
 * @author amaliujia
 */
public class SDConnectionException extends IOException {
    public Throwable detail;

    public SDConnectionException(){
        initCause(null);
    }

    public SDConnectionException(String message){
        super(message);
        initCause(detail);
    }

    public SDConnectionException(String message, Throwable cause){
        super(message);
        this.detail = cause;
    }

    public SDConnectionException(String message, Exception e){
        super(message, e);
    }

    public String getMessage(){
        if(detail == null){
            return super.getMessage();
        }else{
            return super.getMessage() + "; \n\t" + detail.toString();
        }
    }

    public Throwable getCause(){
        return detail;
    }

}
