package com.leonteqsecurity.anonymousdial.Models;

public class Contact {
    private String Name;
    private String phoneNumber;


    public Contact(String name, String phoneNumber) {
        Name = name;
        this.phoneNumber = phoneNumber;
    }

    public Contact() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "Name='" + Name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
