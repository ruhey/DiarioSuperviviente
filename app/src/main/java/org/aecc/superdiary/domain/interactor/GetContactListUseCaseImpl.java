package org.aecc.superdiary.domain.interactor;

import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.ContactRepository;

import java.util.Collection;

import javax.inject.Inject;

public class GetContactListUseCaseImpl implements GetContactListUseCase {

    private final ContactRepository contactRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Callback callback;

    @Inject
    public GetContactListUseCaseImpl(ContactRepository contactRepository, ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {
        this.contactRepository = contactRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override public void execute(Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("Interactor callback cannot be null!!!");
        }
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override public void run() {
        this.contactRepository.getContactList(this.repositoryCallback);
    }

    private final ContactRepository.ContactListCallback repositoryCallback =
            new ContactRepository.ContactListCallback() {
                @Override public void onContactListLoaded(Collection<Contact> contactsCollection) {
                    notifyGetContactListSuccessfully(contactsCollection);
                }

                @Override public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifyGetContactListSuccessfully(final Collection<Contact> contactsCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override public void run() {
                callback.onContactListLoaded(contactsCollection);
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
