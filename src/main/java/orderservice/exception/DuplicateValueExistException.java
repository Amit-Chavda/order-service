package orderservice.exception;

public class DuplicateValueExistException extends RuntimeException {

    public DuplicateValueExistException(String message) {
        super(message);
    }
}
