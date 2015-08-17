package org.aecc.superdiary.data.cache;

import org.aecc.superdiary.data.entity.MedicineEntity;

public interface MedicineCache {

    void get(final int medicineId, final MedicineCacheCallback callback);

    void put(MedicineEntity medicineEntity);

    boolean isCached(final int medicineId);

    boolean isExpired();

    void evictAll();

    interface MedicineCacheCallback {
        void onMedicineEntityLoaded(MedicineEntity medicineEntity);

        void onError(Exception exception);
    }
}
