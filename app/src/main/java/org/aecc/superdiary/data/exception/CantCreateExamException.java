package org.aecc.superdiary.data.exception;


public class CantCreateExamException extends Exception {

    public CantCreateExamException() {
        super();
    }

    public CantCreateExamException(final String message) {
        super(message);
    }

    public CantCreateExamException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantCreateExamException(final Throwable cause) {
        super(cause);
    }
}
