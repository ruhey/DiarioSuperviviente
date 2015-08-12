package org.aecc.superdiary.data.exception;


public class CantCreateContactException extends Exception {

    public CantCreateContactException() {
        super();
    }

    public CantCreateContactException(final String message) {
        super(message);
    }

    public CantCreateContactException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantCreateContactException(final Throwable cause) {
        super(cause);
    }
}
