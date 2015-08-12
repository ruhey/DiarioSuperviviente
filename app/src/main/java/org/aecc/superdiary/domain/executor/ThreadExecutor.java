package org.aecc.superdiary.domain.executor;

public interface ThreadExecutor {

    void execute(final Runnable runnable);
}
