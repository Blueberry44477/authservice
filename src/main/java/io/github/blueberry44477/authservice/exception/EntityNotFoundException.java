package io.github.blueberry44477.authservice.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityName, Object id) {
        super(String.format("%s with id/email [%s] not found", entityName, id));
    }
}
