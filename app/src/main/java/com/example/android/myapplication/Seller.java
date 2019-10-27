package com.example.android.myapplication;

/**
 * Class representing seller attributes
 */
public class Seller extends  User{

//    public ArrayList<Listing> listings;

    public Seller() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Seller(String username, String phoneNumber, String email, boolean isSelling) {
        super(username, phoneNumber, email);
        this.isSelling = isSelling;
//        listings = list;
    }
}
