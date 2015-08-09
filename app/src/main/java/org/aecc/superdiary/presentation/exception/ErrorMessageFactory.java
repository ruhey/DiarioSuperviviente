package org.aecc.superdiary.presentation.exception;

import android.content.Context;

import org.aecc.superdiary.R;
import org.aecc.superdiary.data.exception.ContactNotFoundException;
import org.aecc.superdiary.data.exception.NetworkConnectionException;

public class ErrorMessageFactory {
    private ErrorMessageFactory() {
        //empty
    }


    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.exception_message_generic);

        if (exception instanceof NetworkConnectionException) {
            message = context.getString(R.string.exception_message_no_connection);
        } else if (exception instanceof ContactNotFoundException) {
            message = context.getString(R.string.exception_message_contact_not_found);
        }

        return message;
    }
}
