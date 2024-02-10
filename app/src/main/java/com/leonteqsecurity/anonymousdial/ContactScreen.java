package com.leonteqsecurity.anonymousdial;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;

public class ContactScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_screen);

    }



    @SuppressLint("Range")
    public void ReadContancts(Context context)
    {
        ContentResolver contentResolver = context.getContentResolver();
        String[] contactprojection=new String[]
                {
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME
                };
        Cursor cursor=contentResolver.query(ContactsContract.Contacts.CONTENT_URI,contactprojection,null,null,null);

        if(cursor!=null)
        {
            try {
                while (cursor.moveToNext())
                {
                    String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    System.out.println(displayName);
                }

            }finally {
                cursor.close();
            }

        }
    }
}