package org.aecc.superdiary.data.exception;


public class CantSaveMedicineException extends Exception {

    public CantSaveMedicineException() {
        super();
    }

    public CantSaveMedicineException(final String message) {
        super(message);
    }

    public CantSaveMedicineException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantSaveMedicineException(final Throwable cause) {
        super(cause);
    }
}
