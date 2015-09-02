package org.aecc.superdiary.presentation.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.model.SymptomModel;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.SymptomViewHolder> {

    public interface OnItemClickListener {
        void onSymptomItemClicked(SymptomModel symptomModel);
    }

    private List<SymptomModel> symptomsCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public SymptomsAdapter(Context context, Collection<SymptomModel> symptomsCollection) {
        this.validateSymptomsCollection(symptomsCollection);
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.symptomsCollection = (List<SymptomModel>) symptomsCollection;
    }

    @Override public int getItemCount() {
        return (this.symptomsCollection != null) ? this.symptomsCollection.size() : 0;
    }

    @Override public SymptomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.row_symptom, parent, false);
        SymptomViewHolder symptomViewHolder = new SymptomViewHolder(view);

        return symptomViewHolder;
    }

    @Override public void onBindViewHolder(SymptomViewHolder holder, final int position) {
        final SymptomModel symptomModel = this.symptomsCollection.get(position);
        holder.textViewTitle.setText(symptomModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (SymptomsAdapter.this.onItemClickListener != null) {
                    SymptomsAdapter.this.onItemClickListener.onSymptomItemClicked(symptomModel);
                }
            }
        });
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void setSymptomsCollection(Collection<SymptomModel> symptomsCollection) {
        this.validateSymptomsCollection(symptomsCollection);
        this.symptomsCollection = (List<SymptomModel>) symptomsCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateSymptomsCollection(Collection<SymptomModel> symptomsCollection) {
        if (symptomsCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class SymptomViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.title)
        TextView textViewTitle;

        public SymptomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
