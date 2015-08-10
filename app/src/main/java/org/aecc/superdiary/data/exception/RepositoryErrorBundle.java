package org.aecc.superdiary.data.exception;

import org.aecc.superdiary.domain.exception.ErrorBundle;


public class RepositoryErrorBundle implements ErrorBundle {


    private final Exception exception;

    public RepositoryErrorBundle(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public String getErrorMessage() {
        String message = "";
        if (this.exception != null) {
            this.exception.getMessage();
        }
        return message;
    }


}
