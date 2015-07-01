package ArcherException;

/**
 * @author amaliujia
 */
public class SDMarshallingException extends SDConnectionException {

        public SDMarshallingException(String message){
            super(message);
        }

        public SDMarshallingException(String message, Exception e){
            super(message, e);
        }

}
