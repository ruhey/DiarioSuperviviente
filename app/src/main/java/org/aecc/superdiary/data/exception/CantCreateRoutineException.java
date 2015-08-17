package org.aecc.superdiary.data.exception;


public class CantCreateRoutineException extends Exception {

    public CantCreateRoutineException() {
        super();
    }

    public CantCreateRoutineException(final String message) {
        super(message);
    }

    public CantCreateRoutineException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantCreateRoutineException(final Throwable cause) {
        super(cause);
    }
}
