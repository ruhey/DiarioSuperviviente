package org.aecc.superdiary.domain.interactor.routine;

import org.aecc.superdiary.domain.Routine;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.interactor.routine.SaveRoutineUseCase;
import org.aecc.superdiary.domain.repository.RoutineRepository;

import javax.inject.Inject;


public class SaveRoutineUseCaseImpl implements SaveRoutineUseCase {

    private final RoutineRepository routineRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Routine routine = null;
    private Callback callback;
    private final RoutineRepository.RoutineSaveCallback repositoryCallback =
            new RoutineRepository.RoutineSaveCallback() {
                @Override
                public void onRoutineSaved(Routine routine) {
                    notifySaveRoutineSuccessfully(routine);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public SaveRoutineUseCaseImpl(RoutineRepository routineRepository, ThreadExecutor threadExecutor,
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
        this.routineRepository.saveRoutine(this.routine, this.repositoryCallback);
    }

    private void notifySaveRoutineSuccessfully(final Routine routine) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onRoutineDataSaved(routine);
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
