package org.aecc.superdiary.data.exception;


public class CantSaveRoutineException extends Exception {

    public CantSaveRoutineException() {
        super();
    }

    public CantSaveRoutineException(final String message) {
        super(message);
    }

    public CantSaveRoutineException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantSaveRoutineException(final Throwable cause) {
        super(cause);
    }
}
