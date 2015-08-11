package org.aecc.superdiary.data.exception;


public class CommentNotFoundException extends Exception {

    public CommentNotFoundException() {
        super();
    }

    public CommentNotFoundException(final String message) {
        super(message);
    }

    public CommentNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CommentNotFoundException(final Throwable cause) {
        super(cause);
    }
}
