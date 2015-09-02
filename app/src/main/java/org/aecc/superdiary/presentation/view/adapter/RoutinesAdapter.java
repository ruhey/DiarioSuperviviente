package org.aecc.superdiary.presentation.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.model.RoutineModel;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RoutinesAdapter extends RecyclerView.Adapter<RoutinesAdapter.RoutineViewHolder> {

    public interface OnItemClickListener {
        void onRoutineItemClicked(RoutineModel routineModel);
    }

    private List<RoutineModel> routinesCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public RoutinesAdapter(Context context, Collection<RoutineModel> routinesCollection) {
        this.validateRoutinesCollection(routinesCollection);
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.routinesCollection = (List<RoutineModel>) routinesCollection;
    }

    @Override public int getItemCount() {
        return (this.routinesCollection != null) ? this.routinesCollection.size() : 0;
    }

    @Override public RoutineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.row_routine, parent, false);
        RoutineViewHolder routineViewHolder = new RoutineViewHolder(view);

        return routineViewHolder;
    }

    @Override public void onBindViewHolder(RoutineViewHolder holder, final int position) {
        final RoutineModel routineModel = this.routinesCollection.get(position);
        holder.textViewTitle.setText(routineModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (RoutinesAdapter.this.onItemClickListener != null) {
                    RoutinesAdapter.this.onItemClickListener.onRoutineItemClicked(routineModel);
                }
            }
        });
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void setRoutinesCollection(Collection<RoutineModel> routinesCollection) {
        this.validateRoutinesCollection(routinesCollection);
        this.routinesCollection = (List<RoutineModel>) routinesCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateRoutinesCollection(Collection<RoutineModel> routinesCollection) {
        if (routinesCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class RoutineViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.title)
        TextView textViewTitle;

        public RoutineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
