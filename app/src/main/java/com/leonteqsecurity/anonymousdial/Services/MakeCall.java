package com.leonteqsecurity.anonymousdial.Services;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

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
        int simIndex=1;
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_PHONE_CALL);
        } else {
            String phoneNumber = contact.getPhoneNumber();
            Intent intent = new Intent(Intent.ACTION_CALL);
            if(callType=="private")
            {
                phoneNumber="#31#"+contact.getPhoneNumber();
            } else if (callType=="reverse") {
                phoneNumber="#"+contact.getPhoneNumber();
            }
            intent.setData(Uri.parse("tel:" + phoneNumber));
            // Check if the device is running Android Lollipop (API 21) or higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                intent.putExtra("com.android.phone.extra.slot", simIndex);
            }
            context.startActivity(intent);
        }
    }
}
