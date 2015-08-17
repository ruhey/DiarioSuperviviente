package org.aecc.superdiary.data.exception;


public class MeetingNotFoundException extends Exception {

    public MeetingNotFoundException() {
        super();
    }

    public MeetingNotFoundException(final String message) {
        super(message);
    }

    public MeetingNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MeetingNotFoundException(final Throwable cause) {
        super(cause);
    }
}
