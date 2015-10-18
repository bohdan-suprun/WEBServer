package engine.exceptions;

/**
 * Created by bod on 10.08.15.
 */
public class WrongPackageException extends Exception {
    public WrongPackageException(){
        super();
    }
    public WrongPackageException(String msg){
        super(msg);
    }
}


