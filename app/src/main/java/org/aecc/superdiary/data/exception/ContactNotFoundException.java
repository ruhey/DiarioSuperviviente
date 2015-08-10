package org.aecc.superdiary.data.exception;

/**
 * Created by a555148 on 08/08/2015.
 */
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
