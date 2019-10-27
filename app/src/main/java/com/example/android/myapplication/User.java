package com.example.android.myapplication;

/**
 * Class representing user attributes.
 */
public class User {
    public String username;
    public String phoneNumber;
    public String email;
    public boolean isSelling;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String phoneNumber, String email) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

}