package org.aecc.superdiary.data.exception;


public class CantCreateMedicineException extends Exception {

    public CantCreateMedicineException() {
        super();
    }

    public CantCreateMedicineException(final String message) {
        super(message);
    }

    public CantCreateMedicineException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CantCreateMedicineException(final Throwable cause) {
        super(cause);
    }
}
