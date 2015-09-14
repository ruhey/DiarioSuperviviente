package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.contact.DeleteContactUseCase;
import org.aecc.superdiary.domain.interactor.contact.GetContactDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.ContactModelDataMapper;
import org.aecc.superdiary.presentation.model.ContactModel;
import org.aecc.superdiary.presentation.view.PersonajeDetailView;
import org.aecc.superdiary.presentation.view.PersonajesDeleteView;

import java.util.Collection;

import javax.inject.Inject;

public class ContactDeletePresenter implements Presenter {

    private final GetContactDetailsUseCase getContactDetailsUseCase;
    private final DeleteContactUseCase deleteContactUseCase;
    private final ContactModelDataMapper contactModelDataMapper;
    private int contactId;
    private Contact contact;
    private PersonajesDeleteView viewDetailsView;
    private final GetContactDetailsUseCase.Callback contactDetailsCallback = new GetContactDetailsUseCase.Callback() {
        @Override
        public void onContactDataLoaded(Contact contact) {
            ContactDeletePresenter.this.showContactDetailsInView(contact);
            ContactDeletePresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ContactDeletePresenter.this.hideViewLoading();
            ContactDeletePresenter.this.showErrorMessage(errorBundle);
            ContactDeletePresenter.this.showViewRetry();
        }
    };
    private final DeleteContactUseCase.Callback deleteContactCallback = new DeleteContactUseCase.Callback(){
        @Override
        public void onContactDataDeleted(Collection<Contact> contactsCollection){
            ContactDeletePresenter.this.hideViewLoading();
            ContactDeletePresenter.this.showOkMessage();
            ContactDeletePresenter.this.showViewRetry();
            ContactDeletePresenter.this.goBack();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ContactDeletePresenter.this.hideViewLoading();
            ContactDeletePresenter.this.showErrorMessage(errorBundle);
            ContactDeletePresenter.this.showViewRetry();
        }
    };

    @Inject
    public ContactDeletePresenter(GetContactDetailsUseCase getContactDetailsUseCase,
                                  DeleteContactUseCase deleteContactUseCase,
                                  ContactModelDataMapper contactModelDataMapper){
        this.getContactDetailsUseCase = getContactDetailsUseCase;
        this.deleteContactUseCase = deleteContactUseCase;
        this.contactModelDataMapper = contactModelDataMapper;
    }

    public void setView(@NonNull PersonajesDeleteView view) {
        this.viewDetailsView = view;
    }

    public void initialize(int userId) {
        this.contactId = userId;
        this.loadContactDetails();
    }

    public void deleteContact(ContactModel contactModel){
        this.deleteContactUseCase.execute(this.contact.getContactId(), this.deleteContactCallback);
    }

    private void goBack(){
        this.viewDetailsView.goBack();
    }

    private void loadContactDetails() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getContactDetails();
    }

    private void showViewLoading() {
        this.viewDetailsView.showLoading();
    }

    private void hideViewLoading() {
        this.viewDetailsView.hideLoading();
    }

    private void showViewRetry() {
        this.viewDetailsView.showRetry();
    }

    private void hideViewRetry() {
        this.viewDetailsView.hideRetry();
    }

    private void showOkMessage() {
        this.viewDetailsView.showOKMessage();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.getContext(),
                errorBundle.getException());
        this.viewDetailsView.showError(errorMessage);
    }

    private void showContactDetailsInView(Contact contact) {
        this.contact = contact;
        final ContactModel contactModel = this.contactModelDataMapper.transform(contact);
        this.viewDetailsView.renderContact(contactModel);
    }

    private void getContactDetails() {
        this.getContactDetailsUseCase.execute(this.contactId, this.contactDetailsCallback);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }
}
