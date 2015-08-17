package org.aecc.superdiary.domain.interactor.routine;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.RoutineRepository;

import java.util.Collection;

import javax.inject.Inject;

public class GetRoutineListUseCaseImpl implements org.aecc.superdiary.domain.interactor.routine.GetRoutineListUseCase {

    private final RoutineRepository routineRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;
    private final RoutineRepository.RoutineListCallback repositoryCallback =
            new RoutineRepository.RoutineListCallback() {
                @Override
                public void onRoutineListLoaded(Collection<Routine> routinesCollection) {
                    notifyGetRoutineListSuccessfully(routinesCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public GetRoutineListUseCaseImpl(RoutineRepository routineRepository, ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        this.routineRepository = routineRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Interactor callback cannot be null!!!");
        }
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.routineRepository.getRoutineList(this.repositoryCallback);
    }

    private void notifyGetRoutineListSuccessfully(final Collection<Routine> routinesCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onRoutineListLoaded(routinesCollection);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}
