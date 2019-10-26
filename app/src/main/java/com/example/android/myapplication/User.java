package com.example.android.myapplication;

/**
 * Class representing user attributes.
 */
public class User {
    public String firstName;
    public String phoneNumber;
    public String email;
    public String password;


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String phoneNumber, String email, String password) {
        this.firstName = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
    }

    public User(String name, String phoneNumber, String email) {
        this.firstName = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public User(String name, String password) {
        this.firstName = name;
        this.password = password;
    }
}
