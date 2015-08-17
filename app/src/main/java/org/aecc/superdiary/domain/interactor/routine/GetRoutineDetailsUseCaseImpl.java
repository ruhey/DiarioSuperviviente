package org.aecc.superdiary.domain.interactor.routine;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.RoutineRepository;

import javax.inject.Inject;

public class GetRoutineDetailsUseCaseImpl implements org.aecc.superdiary.domain.interactor.routine.GetRoutineDetailsUseCase {

    private final RoutineRepository routineRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private int routineId = -1;
    private Callback callback;
    private final RoutineRepository.RoutineDetailsCallback repositoryCallback =
            new RoutineRepository.RoutineDetailsCallback() {
                @Override
                public void onRoutineLoaded(Routine routine) {
                    notifyGetRoutineDetailsSuccessfully(routine);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public GetRoutineDetailsUseCaseImpl(RoutineRepository routineRepository, ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        this.routineRepository = routineRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(int routineId, Callback callback) {
        if (routineId < 0 || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.routineId = routineId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.routineRepository.getRoutineById(this.routineId, this.repositoryCallback);
    }

    private void notifyGetRoutineDetailsSuccessfully(final Routine routine) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onRoutineDataLoaded(routine);
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
