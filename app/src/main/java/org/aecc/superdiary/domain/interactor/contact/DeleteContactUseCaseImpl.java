package org.aecc.superdiary.domain.interactor.contact;

import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.ContactRepository;

import java.util.Collection;

import javax.inject.Inject;

public class DeleteContactUseCaseImpl implements DeleteContactUseCase {

    private final ContactRepository contactRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private int contactId = -1;
    private DeleteContactUseCase.Callback callback;
    private final ContactRepository.ContactDetionCallback repositoryCallback =
            new ContactRepository.ContactDetionCallback() {
                @Override
                public void onContactDeleted(Collection<Contact> contactsCollection) {
                    notifyDeleteContactSuccessfully(contactsCollection);
                }

                @Override
                public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    @Inject
    public DeleteContactUseCaseImpl(ContactRepository contactRepository, ThreadExecutor threadExecutor,
                                    PostExecutionThread postExecutionThread) {
        this.contactRepository = contactRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override
    public void execute(int contactId, Callback callback) {
        if (contactId < 0 || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.contactId = contactId;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override
    public void run() {
        this.contactRepository.deleteContact(this.contactId, this.repositoryCallback);
    }

    private void notifyDeleteContactSuccessfully(final Collection<Contact> contactsCollection) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onContactDataDeleted(contactsCollection);
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
