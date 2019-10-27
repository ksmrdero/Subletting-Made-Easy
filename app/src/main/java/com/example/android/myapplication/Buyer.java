package com.example.android.myapplication;

public class Buyer extends User{

    public Buyer() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Buyer(String username, String phoneNumber, String email, boolean isSelling) {
        super(username, phoneNumber, email);
        this.isSelling = isSelling;
    }
}
