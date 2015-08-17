package org.aecc.superdiary.domain.interactor.routine;


import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.RoutineRepository;

import javax.inject.Inject;

public class CreateRoutineUseCaseImpl implements org.aecc.superdiary.domain.interactor.routine.CreateRoutineUseCase {

    private final RoutineRepository routineRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Routine routine = null;
    private Callback callback;
    private final RoutineRepository.RoutineCreationCallback repositoryCallback =
            new RoutineRepository.RoutineCreationCallback() {
                @Override
                public void onRoutineCreated(Routine routine) {
                    notifyCreateRoutineSuccessfully(routine);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public CreateRoutineUseCaseImpl(RoutineRepository routineRepository, ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        this.routineRepository = routineRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(Routine routine, Callback callback) {
        if (routine == null || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.routine = routine;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.routineRepository.createRoutine(this.routine, this.repositoryCallback);
    }

    private void notifyCreateRoutineSuccessfully(final Routine routine) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onRoutineDataCreated(routine);
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
