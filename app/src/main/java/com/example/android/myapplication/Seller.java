package com.example.android.myapplication;

import java.util.ArrayList;

/**
 * Class representing seller attributes
 */
public class Seller {
    public String username;
    public String phoneNumber;
    public String email;
    public String password;
    public ArrayList<String> listings;

    public Seller() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Seller(String username, String phoneNumber, String email) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        listings = new ArrayList<>();
    }

    public ArrayList<String> getListings() {
        return listings;
    }

    public void addListing(String newListing) {
        listings.add(newListing);
    }


}
