package com.example.android.myapplication;

public class Buyer {

    public String username;
    public String phoneNumber;
    public String email;
    public String password;

    public Buyer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Buyer(String username, String phoneNumber, String email) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
