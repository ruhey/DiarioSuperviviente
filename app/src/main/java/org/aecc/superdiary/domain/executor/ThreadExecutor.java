package org.aecc.superdiary.domain.executor;

import org.aecc.superdiary.domain.interactor.Interactor;
public interface ThreadExecutor {

    void execute(final Runnable runnable);
}
