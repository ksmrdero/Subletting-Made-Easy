package com.example.android.myapplication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ListingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Listing");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
