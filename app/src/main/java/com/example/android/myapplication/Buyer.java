package com.example.android.myapplication;

public class Buyer {

    public String userName;
    public String phoneNumber;
    public String email;
    public String password;

    public Buyer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Buyer(String userName, String phoneNumber, String email) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
