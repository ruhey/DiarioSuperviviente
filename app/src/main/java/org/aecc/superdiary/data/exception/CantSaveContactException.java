package org.aecc.superdiary.data.exception;


public class CantSaveContactException extends Exception{

    public CantSaveContactException() {
        super();
    }

    public CantSaveContactException(final String message) {
        super(message);
    }

    public CantSaveContactException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantSaveContactException(final Throwable cause) {
        super(cause);
    }
}
