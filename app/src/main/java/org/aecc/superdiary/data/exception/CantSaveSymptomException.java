package org.aecc.superdiary.data.exception;


public class CantSaveSymptomException extends Exception {

    public CantSaveSymptomException() {
        super();
    }

    public CantSaveSymptomException(final String message) {
        super(message);
    }

    public CantSaveSymptomException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantSaveSymptomException(final Throwable cause) {
        super(cause);
    }
}
