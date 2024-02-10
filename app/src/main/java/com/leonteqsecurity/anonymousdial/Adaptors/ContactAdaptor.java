package com.leonteqsecurity.anonymousdial.Adaptors;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leonteqsecurity.anonymousdial.Models.Contact;
import com.leonteqsecurity.anonymousdial.R;
import com.leonteqsecurity.anonymousdial.Services.MakeCall;

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
        holder.phone_icon_call.setOnClickListener(v->showPopup(contact));
    }

    private void showPopup(Contact contact) {
        MakeCall makeCall= new MakeCall(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.phone_dialog_layout, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
        dialogTitle.setText("Call Confirmation");

        TextView dialogMessage = dialogView.findViewById(R.id.dialog_message);
        dialogMessage.setText("Are you sure you want to call " + contact.getName() + " at " + contact.getPhoneNumber() + "?");

        Button call_private_Button = dialogView.findViewById(R.id.dialog_call_private_button);
        Button call_reverse_Button = dialogView.findViewById(R.id.reverse_call);
        Button call_normal_Button = dialogView.findViewById(R.id.call_normal_button);

        call_reverse_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall.makeACall(contact,"reverse");
                alertDialog.dismiss();
            }
        });
        call_normal_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall.makeACall(contact,"normal");
                alertDialog.dismiss();

            }
        });

        call_private_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeCall.makeACall(contact,"private");
                alertDialog.dismiss();
            }
        });

        Button cancelButton = dialogView.findViewById(R.id.dialog_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView contactName,ContactNumber;
        private ImageView phone_icon_call;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName=itemView.findViewById(R.id.contact_name);
            ContactNumber=itemView.findViewById(R.id.contact_num);
            phone_icon_call=itemView.findViewById(R.id.call_user_icon);
        }
    }
}
