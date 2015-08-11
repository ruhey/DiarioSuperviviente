package org.aecc.superdiary.data.exception;


public class CantSaveCommentException extends Exception {
    public CantSaveCommentException() {
        super();
    }

    public CantSaveCommentException(final String message) {
        super(message);
    }

    public CantSaveCommentException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantSaveCommentException(final Throwable cause) {
        super(cause);
    }
}
