package org.aecc.superdiary.presentation.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.model.ContactModel;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    public interface OnItemClickListener {
        void onContactItemClicked(ContactModel contactModel);
    }

    private List<ContactModel> contactsCollection;
    private final LayoutInflater layoutInflater;

    private OnItemClickListener onItemClickListener;

    public ContactsAdapter(Context context, Collection<ContactModel> contactsCollection) {
        this.validateContactsCollection(contactsCollection);
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.contactsCollection = (List<ContactModel>) contactsCollection;
    }

    @Override public int getItemCount() {
        return (this.contactsCollection != null) ? this.contactsCollection.size() : 0;
    }

    @Override public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.row_contact, parent, false);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);

        return contactViewHolder;
    }

    @Override public void onBindViewHolder(ContactViewHolder holder, final int position) {
        final ContactModel contactModel = this.contactsCollection.get(position);
        holder.textViewTitle.setText(contactModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (ContactsAdapter.this.onItemClickListener != null) {
                    ContactsAdapter.this.onItemClickListener.onContactItemClicked(contactModel);
                }
            }
        });
    }

    @Override public long getItemId(int position) {
        return position;
    }

    public void setContactsCollection(Collection<ContactModel> contactsCollection) {
        this.validateContactsCollection(contactsCollection);
        this.contactsCollection = (List<ContactModel>) contactsCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private void validateContactsCollection(Collection<ContactModel> contactsCollection) {
        if (contactsCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.title)
        TextView textViewTitle;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}
