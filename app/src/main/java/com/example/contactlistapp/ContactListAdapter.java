package com.example.contactlistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {
    class ContactViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameItemView;
        private final TextView emailItemView;
        private final TextView phoneItemView;

        private ContactViewHolder(View itemView) {
            super(itemView);
            nameItemView = itemView.findViewById(R.id.nameView);
            emailItemView = itemView.findViewById(R.id.emailView);
            phoneItemView = itemView.findViewById(R.id.phoneView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Contact> contacts; // Cached copy of contacts

    ContactListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        if (contacts != null) {
            Contact current = contacts.get(position);
            holder.nameItemView.setText(current.getName());
            holder.emailItemView.setText(current.getEmail());
            holder.phoneItemView.setText(current.getPhonenumber());
        } else {
            // Covers the case of data not being ready yet.
            holder.nameItemView.setText("- geen naam -");
            holder.emailItemView.setText(" - geen email -");
            holder.phoneItemView.setText("- geen telefoonnummer -");
        }
    }

    void setContacts(List<Contact> c){
        contacts = c;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // contacts has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (contacts != null)
            return contacts.size();
        else return 0;
    }
}
