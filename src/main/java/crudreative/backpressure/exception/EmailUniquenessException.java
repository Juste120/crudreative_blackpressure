package crudreative.backpressure.exception;

/**
 * @author PAKOU Komi Juste
 * @since 1/8/26
 */
public class EmailUniquenessException extends RuntimeException {
    public EmailUniquenessException(String message) {
        super(message);
    }
}
