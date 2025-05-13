package be.ucll.se.courses.backend.exception;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
