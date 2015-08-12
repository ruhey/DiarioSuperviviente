package org.aecc.superdiary.domain.exception;

/**
 * Created by a555148 on 05/08/2015.
 */
public interface ErrorBundle {

    Exception getException();

    String getErrorMessage();
}
