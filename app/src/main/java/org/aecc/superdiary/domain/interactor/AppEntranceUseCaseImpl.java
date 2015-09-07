package org.aecc.superdiary.domain.interactor;


import org.aecc.superdiary.domain.AppEntranceElement;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.AppEntranceElementDomainRepository;

import java.util.Collection;

import javax.inject.Inject;

public class AppEntranceUseCaseImpl implements AppEntranceUseCase {

    private final AppEntranceElementDomainRepository appEntranceElementDomainRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private Callback callback;

    @Inject
    public AppEntranceUseCaseImpl(AppEntranceElementDomainRepository appEntranceElementDomainRepository, ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread){
        this.appEntranceElementDomainRepository = appEntranceElementDomainRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    private final AppEntranceElementDomainRepository.AppEntranceElementCallback repositoryCallback =
            new AppEntranceElementDomainRepository.AppEntranceElementCallback(){

                @Override
                 public void onAppElementsEntranceItemsLoaded(Collection<AppEntranceElement> appEntranceElementsList){
                    notifyAppElementsEntranceItemsSuccessfully(appEntranceElementsList);
                }

                @Override
                public void onError(ErrorBundle errorBundle){
                    notifyError(errorBundle);
                }

    };

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
        this.appEntranceElementDomainRepository.getAppEntranceElementsList(this.repositoryCallback);
    }

    private void notifyAppElementsEntranceItemsSuccessfully(final Collection<AppEntranceElement> appEntranceElementsList) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onDataLoaded(appEntranceElementsList);
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
