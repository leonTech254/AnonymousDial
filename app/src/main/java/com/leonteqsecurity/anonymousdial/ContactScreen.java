package com.leonteqsecurity.anonymousdial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.leonteqsecurity.anonymousdial.Adaptors.ContactAdaptor;
import com.leonteqsecurity.anonymousdial.Models.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactScreen extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private List<Contact> contactList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_screen);
        recyclerView = findViewById(R.id.recycle_view_contact);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactList = new ArrayList<>();
        readContacts(this);
    }

    @SuppressLint("Range")
    public void readContacts(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        String[] contactProjection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, contactProjection, null, null, null);

        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String contactNumber = getPhoneNumber(contentResolver, contactId);
                    Contact contact = new Contact();
                    contact.setName(displayName);
                    contact.setPhoneNumber(contactNumber);
                    if(contactNumber!=null)
                    {

                        contact.setPhoneNumber(contactNumber.replace("-",""));

                        if(!contactNumber.startsWith("*"))
                        {
                            if(contactNumber.startsWith("07") || contactNumber.startsWith("+254"))
                            {
                                contactList.add(contact);
                            } else if (contactNumber.startsWith("7") && contactNumber.length()==9) {
                                contact.setPhoneNumber("0"+contactNumber);
                                contactList.add(contact);
                            }

                        }

                    }
                }
            } finally {
                cursor.close();
            }
            adapter = new ContactAdaptor(sortContactsAlphabetically(contactList), this);
            recyclerView.setAdapter(adapter);
        }
    }

    public List<Contact> sortContactsAlphabetically(List<Contact> contacts) {
        Collections.sort(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact contact1, Contact contact2) {
                return contact1.getName().compareToIgnoreCase(contact2.getName());
            }
        });
        return contacts;
    }

    @SuppressLint("Range")
    private String getPhoneNumber(ContentResolver contentResolver, String contactId) {
        Cursor phoneCursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{contactId},
                null
        );

        if (phoneCursor != null) {
            try {
                if (phoneCursor.moveToFirst()) {
                    return phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
            } finally {
                phoneCursor.close();
            }
        }
        return null;
    }
}
