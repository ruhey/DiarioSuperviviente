package org.aecc.superdiary.data.exception;


public class CantCreateSymptomException extends Exception {

    public CantCreateSymptomException() {
        super();
    }

    public CantCreateSymptomException(final String message) {
        super(message);
    }

    public CantCreateSymptomException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantCreateSymptomException(final Throwable cause) {
        super(cause);
    }
}
