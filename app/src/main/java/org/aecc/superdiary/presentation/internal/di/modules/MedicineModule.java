package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.medicine.GetMedicineDetailsUseCase;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineListUseCase;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineListUseCaseImpl;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.presenter.MedicineListPresenter;
import org.aecc.superdiary.presentation.presenter.Presenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MedicineModule {

    @Provides
    @PerActivity
    GetMedicineListUseCase provideGetMedicineListUseCase(GetMedicineListUseCaseImpl getMedicineListUseCase) {
        return getMedicineListUseCase;
    }

    @Provides
    @PerActivity
    GetMedicineDetailsUseCase provideGetMedicineDetailsUseCase(GetMedicineDetailsUseCaseImpl getMedicineDetailsUseCase) {
        return getMedicineDetailsUseCase;
    }

    @Provides
    @PerActivity
    Presenter provideMedicineListPresenter(MedicineListPresenter medicineListPresenter) {
        return medicineListPresenter;
    }

}

