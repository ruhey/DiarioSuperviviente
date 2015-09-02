package org.aecc.superdiary.presentation.exception;

import android.content.Context;

import org.aecc.superdiary.R;
import org.aecc.superdiary.data.exception.ContactNotFoundException;
import org.aecc.superdiary.data.exception.ExamNotFoundException;
import org.aecc.superdiary.data.exception.MedicineNotFoundException;
import org.aecc.superdiary.data.exception.MeetingNotFoundException;
import org.aecc.superdiary.data.exception.NetworkConnectionException;
import org.aecc.superdiary.data.exception.RoutineNotFoundException;
import org.aecc.superdiary.data.exception.SymptomNotFoundException;

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
        } else if (exception instanceof MeetingNotFoundException) {
            message = context.getString(R.string.exception_message_meeting_not_found);
        } else if (exception instanceof MedicineNotFoundException) {
            message = context.getString(R.string.exception_message_medicine_not_found);
        } else if (exception instanceof RoutineNotFoundException) {
            message = context.getString(R.string.exception_message_routine_not_found);
        } else if (exception instanceof ExamNotFoundException) {
            message = context.getString(R.string.exception_message_exam_not_found);
        } else if (exception instanceof SymptomNotFoundException) {
            message = context.getString(R.string.exception_message_symptom_not_found);
        }

        return message;
    }
}
