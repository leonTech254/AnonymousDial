package com.leonteqsecurity.anonymousdial.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leonteqsecurity.anonymousdial.Models.Contact;
import com.leonteqsecurity.anonymousdial.R;

import java.util.List;

public class ContactAdaptor extends RecyclerView.Adapter<ContactAdaptor.ContactViewHolder> {
    List<Contact> contactList;
    private Context context;
    public  ContactAdaptor(List<Contact> contacts, Context context)
    {
        this.contactList=contacts;
        this.context=context;

    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_design_list, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact=contactList.get(position);
        holder.contactName.setText(contact.getName());
        holder.ContactNumber.setText(contact.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView contactName,ContactNumber;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName=itemView.findViewById(R.id.contact_name);
            ContactNumber=itemView.findViewById(R.id.contact_num);
        }
    }
}
