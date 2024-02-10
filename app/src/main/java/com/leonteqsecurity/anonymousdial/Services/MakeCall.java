package com.leonteqsecurity.anonymousdial.Services;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.leonteqsecurity.anonymousdial.Models.Contact;

public class MakeCall {
    private static final int REQUEST_PHONE_CALL = 1;
    private Context context;

    public MakeCall(Context context) {
        this.context = context;
    }

    public void makeACall(Contact contact, String callType) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_PHONE_CALL);
        } else {
            String phoneNumber = contact.getPhoneNumber();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            context.startActivity(intent);
        }
    }
}
