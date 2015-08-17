package org.aecc.superdiary.data.exception;


public class CantSaveExamException extends Exception {

    public CantSaveExamException() {
        super();
    }

    public CantSaveExamException(final String message) {
        super(message);
    }

    public CantSaveExamException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantSaveExamException(final Throwable cause) {
        super(cause);
    }
}
