package org.aecc.superdiary.presentation;

import android.os.Handler;
import android.os.Looper;

import org.aecc.superdiary.domain.executor.PostExecutionThread;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UIThread implements PostExecutionThread {

    private final Handler handler;

    @Inject
    public UIThread() {
        this.handler = new Handler(Looper.getMainLooper());
    }

    /**
     * Causes the Runnable r to be added to the message queue.
     * The runnable will be run on the main thread.
     *
     * @param runnable {@link Runnable} to be executed.
     */
    @Override public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
