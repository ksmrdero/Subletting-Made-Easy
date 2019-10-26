package com.example.android.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    TODO: an Adapter class and create an instance of it
//    https://developer.android.com/reference/android/widget/ListView
//    https://developer.android.com/guide/topics/ui/declaring-layout.html#FillingTheLayout

//    Listing
    class Listing {
        String name;
        String address;
        double price;

        Listing(String name, String address, double price) {
            this.name = name;
            this.address = address;
        }
    }
}
