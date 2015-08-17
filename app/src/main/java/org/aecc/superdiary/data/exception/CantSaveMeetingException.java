package org.aecc.superdiary.data.exception;


public class CantSaveMeetingException extends Exception {

    public CantSaveMeetingException() {
        super();
    }

    public CantSaveMeetingException(final String message) {
        super(message);
    }

    public CantSaveMeetingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantSaveMeetingException(final Throwable cause) {
        super(cause);
    }
}
