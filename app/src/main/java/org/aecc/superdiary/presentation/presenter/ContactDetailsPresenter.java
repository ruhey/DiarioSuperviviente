package org.aecc.superdiary.presentation.presenter;


import android.support.annotation.NonNull;

import org.aecc.superdiary.domain.Contact;
import org.aecc.superdiary.domain.exception.ErrorBundle;
import org.aecc.superdiary.domain.interactor.contact.GetContactDetailsUseCase;
import org.aecc.superdiary.presentation.exception.ErrorMessageFactory;
import org.aecc.superdiary.presentation.mapper.ContactModelDataMapper;
import org.aecc.superdiary.presentation.model.ContactModel;
import org.aecc.superdiary.presentation.view.PersonajeDetailView;

import javax.inject.Inject;

public class ContactDetailsPresenter implements Presenter {
    private final GetContactDetailsUseCase getContactDetailsUseCase;
    private final ContactModelDataMapper contactModelDataMapper;
    private int contactId;
    private PersonajeDetailView viewDetailsView;
    private final GetContactDetailsUseCase.Callback contactDetailsCallback = new GetContactDetailsUseCase.Callback() {
        @Override
        public void onContactDataLoaded(Contact contact) {
            ContactDetailsPresenter.this.showContactDetailsInView(contact);
            ContactDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            ContactDetailsPresenter.this.hideViewLoading();
            ContactDetailsPresenter.this.showErrorMessage(errorBundle);
            ContactDetailsPresenter.this.showViewRetry();
        }
    };

    @Inject
    public ContactDetailsPresenter(GetContactDetailsUseCase getContactDetailsUseCase,
                                   ContactModelDataMapper contactModelDataMapper) {
        this.getContactDetailsUseCase = getContactDetailsUseCase;
        this.contactModelDataMapper = contactModelDataMapper;
    }

    public void setView(@NonNull PersonajeDetailView view) {
        this.viewDetailsView = view;
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    public void initialize(int userId) {
        this.contactId = userId;
        this.loadContactDetails();
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

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(this.viewDetailsView.getContext(),
                errorBundle.getException());
        this.viewDetailsView.showError(errorMessage);
    }

    private void showContactDetailsInView(Contact contact) {
        final ContactModel contactModel = this.contactModelDataMapper.transform(contact);
        this.viewDetailsView.renderContact(contactModel);
    }

    private void getContactDetails() {
        this.getContactDetailsUseCase.execute(this.contactId, this.contactDetailsCallback);
    }
}
