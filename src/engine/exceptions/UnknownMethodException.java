package engine.exceptions;

/**
 * Created by bod on 10.08.15.
 */
public class UnknownMethodException extends Exception {
    public UnknownMethodException(){

    }

    public UnknownMethodException(String m){
        super(m);
    }
}
