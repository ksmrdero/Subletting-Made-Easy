package com.example.android.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.list_view) ListView listView;

    private ListingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fab.setOnClickListener(view -> {
            Intent listingIntent = new Intent(MainActivity.this, ListingActivity.class);
            startActivity(listingIntent);
        });

        ArrayList<Listing> mockListings = new ArrayList<>();
        mockListings.add(new Listing("One", "12345 Yeet St.", 100.0, 3, "Desc."));
        mockListings.add(new Listing("Two", "12345 Yeet St.", 100.0, 3, "Desc."));
        mockListings.add(new Listing("Three", "12345 Yeet St.", 100.0, 3, "Desc.", Drawable.createFromPath("../drawable/test.jpg")));

        mAdapter = new ListingAdapter(this, mockListings);
        listView.setAdapter(mAdapter);

    }

//    TODO: an Adapter class and create an instance of it
//    https://developer.android.com/reference/android/widget/ListView
//    https://developer.android.com/guide/topics/ui/declaring-layout.html#FillingTheLayout

//    Listing
    class Listing {
        String name;
        String address;
        double price;
        int numRooms;
        String description;
        Drawable image = null;

        Listing(String name, String address, double price, int numRooms, String description) {
            this.name = name;
            this.address = address;
            this.price = price;
            this.numRooms = numRooms;
            this.description = description;
        }

        Listing(String name, String address, double price, int numRooms, String description, Drawable image) {
            this.name = name;
            this.address = address;
            this.price = price;
            this.numRooms = numRooms;
            this.description = description;
            this.image = image;
        }


        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public double getPrice() {
            return price;
        }

        public int getNumRooms() {
            return numRooms;
        }

        public String getDescription() {
            return description;
        }

        public Drawable getImage() { return image; }
}
}
