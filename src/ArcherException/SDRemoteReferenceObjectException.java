package ArcherException;

import java.io.IOException;

/**
 * Created by amaliujia on 14-12-24.
 */
public class SDRemoteReferenceObjectException extends IOException {

    public Throwable detail;

    public SDRemoteReferenceObjectException(){
        initCause(null);
    }

    public SDRemoteReferenceObjectException(String message){
        super(message);
        initCause(detail);
    }

    public SDRemoteReferenceObjectException(String message, Throwable cause){
        super(message);
        this.detail = cause;
    }

    public SDRemoteReferenceObjectException(String message, Exception e){
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
