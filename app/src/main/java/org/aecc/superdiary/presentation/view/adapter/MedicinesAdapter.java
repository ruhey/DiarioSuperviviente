package org.aecc.superdiary.presentation.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.model.MedicineModel;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.MedicineViewHolder> {

    public interface OnItemClickListener {
        void onMedicineItemClicked(MedicineModel medicineModel);
    }

    private List<MedicineModel> medicinesCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public MedicinesAdapter(Context context, Collection<MedicineModel> medicinesCollection) {
        this.validateMedicinesCollection(medicinesCollection);
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.medicinesCollection = (List<MedicineModel>) medicinesCollection;
    }

    @Override public int getItemCount() {
        return (this.medicinesCollection != null) ? this.medicinesCollection.size() : 0;
    }

    @Override public MedicineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.row_medicine, parent, false);
        MedicineViewHolder medicineViewHolder = new MedicineViewHolder(view);

        return medicineViewHolder;
    }

    @Override public void onBindViewHolder(MedicineViewHolder holder, final int position) {
        final MedicineModel medicineModel = this.medicinesCollection.get(position);
        holder.textViewTitle.setText(medicineModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (MedicinesAdapter.this.onItemClickListener != null) {
                    MedicinesAdapter.this.onItemClickListener.onMedicineItemClicked(medicineModel);
                }
            }
        });
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void setMedicinesCollection(Collection<MedicineModel> medicinesCollection) {
        this.validateMedicinesCollection(medicinesCollection);
        this.medicinesCollection = (List<MedicineModel>) medicinesCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateMedicinesCollection(Collection<MedicineModel> medicinesCollection) {
        if (medicinesCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class MedicineViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.title)
        TextView textViewTitle;

        public MedicineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
