package ro.veltanmarius.mkcustomer.exceptions;

/**
 * @author Marius Veltan
 */
public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String message) {
        super(message);
    }

}