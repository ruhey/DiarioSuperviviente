package org.aecc.superdiary.domain.interactor.contact;

import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.executor.PostExecutionThread;
import org.aecc.superdiary.domain.executor.ThreadExecutor;
import org.aecc.superdiary.domain.repository.ContactRepository;

import javax.inject.Inject;


public class SaveContactUseCaseImpl implements SaveContactUseCase {

    private final ContactRepository contactRepository;
    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;

    private Contact contact = null;
    private SaveContactUseCase.Callback callback;

    @Inject
    public SaveContactUseCaseImpl(ContactRepository contactRepository, ThreadExecutor threadExecutor,
                                        PostExecutionThread postExecutionThread) {
        this.contactRepository = contactRepository;
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    @Override public void execute(Contact contact, Callback callback) {
        if (contact == null || callback == null) {
            throw new IllegalArgumentException("Invalid parameter!!!");
        }
        this.contact = contact;
        this.callback = callback;
        this.threadExecutor.execute(this);
    }

    @Override public void run() {
        this.contactRepository.saveContact(this.contact, this.repositoryCallback);
    }

    private final ContactRepository.ContactSaveCallback repositoryCallback =
            new ContactRepository.ContactSaveCallback() {
                @Override public void onContactSaved(Contact contact) {
                    notifySaveContactSuccessfully(contact);
                }

                @Override public void onError(ErrorBundle errorBundle) {
                    notifyError(errorBundle);
                }
            };

    private void notifySaveContactSuccessfully(final Contact contact) {
        this.postExecutionThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onContactDataSaved(contact);
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
