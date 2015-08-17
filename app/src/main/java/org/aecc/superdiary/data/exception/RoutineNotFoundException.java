package org.aecc.superdiary.data.exception;


public class RoutineNotFoundException extends Exception {

    public RoutineNotFoundException() {
        super();
    }

    public RoutineNotFoundException(final String message) {
        super(message);
    }

    public RoutineNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RoutineNotFoundException(final Throwable cause) {
        super(cause);
    }
}
