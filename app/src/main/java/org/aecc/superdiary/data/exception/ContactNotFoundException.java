package org.aecc.superdiary.data.exception;


public class ContactNotFoundException extends Exception {

    public ContactNotFoundException() {
        super();
    }

    public ContactNotFoundException(final String message) {
        super(message);
    }

    public ContactNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ContactNotFoundException(final Throwable cause) {
        super(cause);
    }
}
