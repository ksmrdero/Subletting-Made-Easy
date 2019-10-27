package com.example.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ListingActivity extends AppCompatActivity implements View.OnClickListener {

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    // [END declare_database_ref]

    private EditText mAddress;
    private EditText mPrice;
    private EditText mNumRooms;
    private EditText mDescription;

    private Button mSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Listing");
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mAddress = findViewById(R.id.input_address);
        mPrice = findViewById(R.id.input_price);
        mNumRooms = findViewById(R.id.input_rooms);
        mDescription = findViewById(R.id.input_description);
        mSubmitBtn = findViewById(R.id.submit_listing_button);

        mSubmitBtn.setOnClickListener(this);
    }

    private void addListing() {
        if (!validateForm()) {
            return;
        }

        String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        writeListing(userId);
        startActivity(new Intent(ListingActivity.this, MainActivity.class));
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mAddress.getText().toString())) {
            mAddress.setError("Required");
            result = false;
        } else {
            mAddress.setError(null);
        }

        if (TextUtils.isEmpty(mPrice.getText().toString())) {
            mPrice.setError("Required");
            result = false;
        } else {
            mPrice.setError(null);
        }

        if (TextUtils.isEmpty(mNumRooms.getText().toString())) {
            mNumRooms.setError("Required");
            result = false;
        } else {
            mNumRooms.setError(null);
        }

        if (TextUtils.isEmpty(mDescription.getText().toString())) {
            mDescription.setError("Required");
            result = false;
        } else {
            mDescription.setError(null);
        }

        return result;
    }

    public void writeListing(String userId) {
        String address = mAddress.getText().toString();
        int price = Integer.parseInt( mPrice.getText().toString() );
        int numRooms = Integer.parseInt( mNumRooms.getText().toString() );
        String description = mDescription.getText().toString();
        Listing listing = new Listing(address, price, numRooms, description);
        mDatabase.child("user").child(userId).child("listing").child(address).setValue(listing);
        mDatabase.child("listings").child(address).setValue(listing);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.submit_listing_button) {
            addListing();
        }
    }

}
