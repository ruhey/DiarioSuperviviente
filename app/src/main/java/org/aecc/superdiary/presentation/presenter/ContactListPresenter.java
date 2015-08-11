package org.aecc.superdiary.presentation.presenter;

import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.contact.GetContactListUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.mapper.ContactModelDataMapper;
import org.aecc.superdiary.presentation.model.ContactModel;
import org.aecc.superdiary.presentation.view.PersonajesListView;

import java.util.Collection;

import javax.inject.Inject;

@PerActivity
public class ContactListPresenter implements Presenter{
    private PersonajesListView viewListView;

    private final GetContactListUseCase getContactListUseCase;
    private final ContactModelDataMapper contactModelDataMapper;

    @Inject
    public ContactListPresenter(GetContactListUseCase getContactListUserCase,
                                ContactModelDataMapper contactModelDataMapper) {
        this.getContactListUseCase = getContactListUserCase;
        this.contactModelDataMapper = contactModelDataMapper;
    }

    public void setView(@NonNull PersonajesListView view) {
        this.viewListView = view;
    }

    @Override public void resume() {}

    @Override public void pause() {}

    public void initialize() {
        this.loadContactList();
    }

    private void loadContactList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getContactList();
    }

    public void onContactClicked(ContactModel contactModel) {
        this.viewListView.viewContact(contactModel);
    }

    private void showViewLoading() {
        this.viewListView.showLoading();
    }

    private void hideViewLoading() {
        this.viewListView.hideLoading();
    }

    private void showViewRetry() {
        this.viewListView.showRetry();
    }

    private void hideViewRetry() {
        this.viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewListView.getContext(),
                errorBundle.getException());
        this.viewListView.showError(errorMessage);
    }

    private void showContactsCollectionInView(Collection<Contact> contactsCollection) {
        final Collection<ContactModel> contactModelsCollection =
                this.contactModelDataMapper.transform(contactsCollection);
        this.viewListView.renderContactList(contactModelsCollection);
    }

    private void getContactList() {
        this.getContactListUseCase.execute(contactListCallback);
    }

    private final GetContactListUseCase.Callback contactListCallback = new GetContactListUseCase.Callback() {
        @Override public void onContactListLoaded(Collection<Contact> contactsCollection) {
            ContactListPresenter.this.showContactsCollectionInView(contactsCollection);
            ContactListPresenter.this.hideViewLoading();
        }
        @Override public void onError(ErrorBundle errorBundle) {
            ContactListPresenter.this.hideViewLoading();
            ContactListPresenter.this.showErrorMessage(errorBundle);
            ContactListPresenter.this.showViewRetry();
        }
    };
}
