package ArcherException;

/**
 * Created by amaliujia on 14-12-23.
 */
public class SDUnmarshallingException extends SDConnectionException{

    public SDUnmarshallingException(String message){
        super(message);
    }

    public SDUnmarshallingException(String message, Exception e){
        super(message, e);
    }
}
