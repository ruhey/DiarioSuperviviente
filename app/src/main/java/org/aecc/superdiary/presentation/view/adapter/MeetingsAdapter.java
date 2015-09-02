package org.aecc.superdiary.presentation.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.model.MeetingModel;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MeetingsAdapter extends RecyclerView.Adapter<MeetingsAdapter.MeetingViewHolder> {

    public interface OnItemClickListener {
        void onMeetingItemClicked(MeetingModel meetingModel);
    }

    private List<MeetingModel> meetingsCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public MeetingsAdapter(Context context, Collection<MeetingModel> meetingsCollection) {
        this.validateMeetingsCollection(meetingsCollection);
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.meetingsCollection = (List<MeetingModel>) meetingsCollection;
    }

    @Override public int getItemCount() {
        return (this.meetingsCollection != null) ? this.meetingsCollection.size() : 0;
    }

    @Override public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.row_meeting, parent, false);
        MeetingViewHolder meetingViewHolder = new MeetingViewHolder(view);

        return meetingViewHolder;
    }

    @Override public void onBindViewHolder(MeetingViewHolder holder, final int position) {
        final MeetingModel meetingModel = this.meetingsCollection.get(position);
        holder.textViewTitle.setText(meetingModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (MeetingsAdapter.this.onItemClickListener != null) {
                    MeetingsAdapter.this.onItemClickListener.onMeetingItemClicked(meetingModel);
                }
            }
        });
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void setMeetingsCollection(Collection<MeetingModel> meetingsCollection) {
        this.validateMeetingsCollection(meetingsCollection);
        this.meetingsCollection = (List<MeetingModel>) meetingsCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateMeetingsCollection(Collection<MeetingModel> meetingsCollection) {
        if (meetingsCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class MeetingViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.title)
        TextView textViewTitle;

        public MeetingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
