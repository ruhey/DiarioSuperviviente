package org.aecc.superdiary.presentation.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.model.ExamModel;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ExamsAdapter extends RecyclerView.Adapter<ExamsAdapter.ExamViewHolder> {

    public interface OnItemClickListener {
        void onExamItemClicked(ExamModel examModel);
    }

    private List<ExamModel> examsCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public ExamsAdapter(Context context, Collection<ExamModel> examsCollection) {
        this.validateExamsCollection(examsCollection);
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.examsCollection = (List<ExamModel>) examsCollection;
    }

    @Override public int getItemCount() {
        return (this.examsCollection != null) ? this.examsCollection.size() : 0;
    }

    @Override public ExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.row_exam, parent, false);
        ExamViewHolder examViewHolder = new ExamViewHolder(view);

        return examViewHolder;
    }

    @Override public void onBindViewHolder(ExamViewHolder holder, final int position) {
        final ExamModel examModel = this.examsCollection.get(position);
        holder.textViewTitle.setText(examModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (ExamsAdapter.this.onItemClickListener != null) {
                    ExamsAdapter.this.onItemClickListener.onExamItemClicked(examModel);
                }
            }
        });
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void setExamsCollection(Collection<ExamModel> examsCollection) {
        this.validateExamsCollection(examsCollection);
        this.examsCollection = (List<ExamModel>) examsCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateExamsCollection(Collection<ExamModel> examsCollection) {
        if (examsCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class ExamViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.title)
        TextView textViewTitle;

        public ExamViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
