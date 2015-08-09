package org.aecc.superdiary.domain.executor;

/**
 * Created by a555148 on 05/08/2015.
 */
public interface PostExecutionThread {

    void post(Runnable runnable);
}
