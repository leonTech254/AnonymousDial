package com.leonteqsecurity.anonymousdial.Services;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.leonteqsecurity.anonymousdial.Models.Contact;

public class MakeCall {

    private static final int REQUEST_CALL_PHONE = 1;

    private final Context context;

    public MakeCall(Context context) {
        this.context = context;
    }

    public void makeACall(Contact contact,String calltype) {

        if (contact == null || contact.getPhoneNumber() == null || contact.getPhoneNumber().isEmpty()) {
            Toast.makeText(context, "Contact phone number is not available", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
            return;
        }

        String phoneNumber = processPhoneNumber(contact.getPhoneNumber());

        if(calltype.equals("reverse"))
        {
            phoneNumber=Uri.encode("#"+phoneNumber);;


        }else if(calltype.equals("private"))
        {
            phoneNumber =Uri.encode("#31#"+phoneNumber);;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "No application available to make a call", Toast.LENGTH_SHORT).show();
        }
    }

    public String processPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            Toast.makeText(context, "Phone number is empty", Toast.LENGTH_SHORT).show();
            return null;
        }

        phoneNumber = phoneNumber.trim();
        phoneNumber=phoneNumber.replaceAll(" ","");
        System.out.println("phone number length is "+phoneNumber.length() +"\n Phone number is "+phoneNumber);

        if (phoneNumber.length() != 13 && phoneNumber.length() != 10) {
            Toast.makeText(context, "Phone number length is invalid", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (phoneNumber.length() == 13) {
            if (!phoneNumber.startsWith("+254")) {
                Toast.makeText(context, "Invalid phone number format", Toast.LENGTH_SHORT).show();
                return null;
            } else {
                return phoneNumber.replace("+254", "0");
            }
        } else if (phoneNumber.length() == 10) {
            if (phoneNumber.startsWith("07")) {
                return phoneNumber;
            } else {
                Toast.makeText(context, "Invalid phone number format", Toast.LENGTH_SHORT).show();
                return null;
            }
        } else {
            Toast.makeText(context, "Invalid phone number", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}
