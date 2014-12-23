package ArcherException;

/**
 * Created by amaliujia on 14-12-23.
 */
public class SDMarshallingException extends SDConnectionException {

        public SDMarshallingException(String message){
            super(message);
        }

        public SDMarshallingException(String message, Exception e){
            super(message, e);
        }

}
