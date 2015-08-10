package org.aecc.superdiary.domain.interactor;

import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.ContactRepository;

import javax.inject.Inject;

/**
 * Created by a555148 on 09/08/2015.
 */
public class GetContactDetailsUseCaseImpl implements GetContactDetailsUseCase {

    private final ContactRepository contactRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private int contactId = -1;
    private GetContactDetailsUseCase.Callback callback;

    @Inject
    public GetContactDetailsUseCaseImpl(ContactRepository contactRepository, ThreadExecutor threadExecutor,
                                     PostExecutionThread postExecutionThread) {
        this.contactRepository = contactRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override public void execute(int contactId, Callback callback) {
        if (contactId < 0 || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.contactId = contactId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override public void run() {
        this.contactRepository.getContactById(this.contactId, this.repositoryCallback);
    }

    private final ContactRepository.ContactDetailsCallback repositoryCallback =
            new ContactRepository.ContactDetailsCallback() {
                @Override public void onContactLoaded(Contact contact) {
                    notifyGetContactDetailsSuccessfully(contact);
                }

                @Override public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetContactDetailsSuccessfully(final Contact contact) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onContactDataLoaded(contact);
            }
        });
    }

    private void notifyError(final ErrorBundle errorBundle) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onError(errorBundle);
            }
        });
    }
}
