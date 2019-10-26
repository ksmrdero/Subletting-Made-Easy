package com.example.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent listingIntent = new Intent(MainActivity.this, ListingActivity.class);
                startActivity(listingIntent);
            }
        });
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

        Listing(String name, String address, double price, int numRooms, String description) {
            this.name = name;
            this.address = address;
            this.price = price;
            this.numRooms = numRooms;
            this.description = description;
        }
    }
}
