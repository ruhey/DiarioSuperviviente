package org.aecc.superdiary.presentation.internal.di.modules;

import org.aecc.superdiary.domain.interactor.medicine.GetMedicineDetailsUseCase;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineListUseCase;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineListUseCaseImpl;
import org.aecc.superdiary.domain.interactor.medicine.CreateMedicineUseCase;
import org.aecc.superdiary.domain.interactor.medicine.DeleteMedicineUseCase;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineDetailsUseCase;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineDetailsUseCaseImpl;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineListUseCase;
import org.aecc.superdiary.domain.interactor.medicine.GetMedicineListUseCaseImpl;
import org.aecc.superdiary.domain.interactor.medicine.SaveMedicineUseCase;
import org.aecc.superdiary.presentation.internal.di.PerActivity;
import org.aecc.superdiary.presentation.presenter.MedicineListPresenter;
import org.aecc.superdiary.presentation.presenter.Presenter;
import org.aecc.superdiary.presentation.presenter.MedicineDetailCreatePresenter;
import org.aecc.superdiary.presentation.presenter.MedicineDetailDeletePresenter;
import org.aecc.superdiary.presentation.presenter.MedicineDetailEditPresenter;
import org.aecc.superdiary.presentation.presenter.MedicineListPresenter;

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
    CreateMedicineUseCase provideCreateMedicineUseCase(CreateMedicineUseCase createMedicineUseCase) {
        return createMedicineUseCase;
    }

    @Provides
    @PerActivity
    DeleteMedicineUseCase provideDeleteMedicineUseCase(DeleteMedicineUseCase deleteMedicineUseCase) {
        return deleteMedicineUseCase;
    }

    @Provides
    @PerActivity
    SaveMedicineUseCase provideSaveMedicineUseCase(SaveMedicineUseCase saveMedicineUseCase) {
        return saveMedicineUseCase;
    }

    @Provides
    @PerActivity
    Presenter provideMedicineListPresenter(MedicineListPresenter medicineListPresenter) {
        return medicineListPresenter;
    }

    @Provides
    @PerActivity
    Presenter providesMedicineDetailEditPresenter(MedicineDetailEditPresenter medicineDetailEditPresenter){
        return medicineDetailEditPresenter;
    }

    @Provides
    @PerActivity
    Presenter providesMedicineDetailDeletePresenter(MedicineDetailDeletePresenter medicineDetailDeletePresenter){
        return medicineDetailDeletePresenter;
    }

    @Provides
    @PerActivity
    Presenter providesMedicineDetailCreatePresenter(MedicineDetailCreatePresenter medicineDetailCreatePresenter){
        return medicineDetailCreatePresenter;
    }

}

