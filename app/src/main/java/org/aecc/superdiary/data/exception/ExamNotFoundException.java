package org.aecc.superdiary.data.exception;


public class ExamNotFoundException extends Exception {

    public ExamNotFoundException() {
        super();
    }

    public ExamNotFoundException(final String message) {
        super(message);
    }

    public ExamNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ExamNotFoundException(final Throwable cause) {
        super(cause);
    }
}
