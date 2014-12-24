package ArcherException;

/**
 * Created by amaliujia on 14-12-24.
 */
public class SDServiceNotExist extends Exception {
    public SDServiceNotExist(){
        super();
    }

    public SDServiceNotExist(String message){
        super(message);
    }
}
