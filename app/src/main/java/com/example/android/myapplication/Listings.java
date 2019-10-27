package com.example.android.myapplication;

public class Listings {
    public String streetAddress;
    public String city;
    public String country;
    public String zip;

    public int price;
    public int occupancy;
    public String description;

    public boolean canNegotiate;
    public int minimumCredit;
    public String duration;

    public Listings(){

    }

    public Listings(String streetAddress, String city, String country, String zip, int price, int occupancy,
                    String description, boolean canNegotiate, int minimumCredit, String duration) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.country = country;
        this.zip = zip;
        this.price = price;
        this.occupancy = occupancy;
        this.description = description;
        this.canNegotiate = canNegotiate;
        this.minimumCredit = minimumCredit;
        this.duration = duration;
    }
}
