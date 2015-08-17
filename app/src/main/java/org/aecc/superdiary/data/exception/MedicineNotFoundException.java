package org.aecc.superdiary.data.exception;


public class MedicineNotFoundException extends Exception {

    public MedicineNotFoundException() {
        super();
    }

    public MedicineNotFoundException(final String message) {
        super(message);
    }

    public MedicineNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MedicineNotFoundException(final Throwable cause) {
        super(cause);
    }
}
