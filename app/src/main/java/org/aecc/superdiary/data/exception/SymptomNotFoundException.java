package org.aecc.superdiary.data.exception;


public class SymptomNotFoundException extends Exception {

    public SymptomNotFoundException() {
        super();
    }

    public SymptomNotFoundException(final String message) {
        super(message);
    }

    public SymptomNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SymptomNotFoundException(final Throwable cause) {
        super(cause);
    }
}
