package org.aecc.superdiary.data.exception;


public class CantCreateCommentException extends Exception {

    public CantCreateCommentException() {
        super();
    }

    public CantCreateCommentException(final String message) {
        super(message);
    }

    public CantCreateCommentException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantCreateCommentException(final Throwable cause) {
        super(cause);
    }
}
