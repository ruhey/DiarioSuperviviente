package org.aecc.superdiary.data.exception;


public class CantCreateMeetingException extends Exception {

    public CantCreateMeetingException() {
        super();
    }

    public CantCreateMeetingException(final String message) {
        super(message);
    }

    public CantCreateMeetingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantCreateMeetingException(final Throwable cause) {
        super(cause);
    }
}
