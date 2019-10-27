package com.example.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.list_view) ListView listView;

    private ListingAdapter mAdapter;
    private DatabaseReference mDatabase;
    private ArrayList<Listing> mockListings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mDatabase = FirebaseDatabase.getInstance().getReference();


//        addDefaultListings();
        addNewListings();

//        System.out.println(mockListings);
        MainActivity thisObject = this;
        System.out.println("before");
        fab.setOnClickListener(view -> {
            Intent listingIntent = new Intent(MainActivity.this, ListingActivity.class);
            startActivity(listingIntent);
        });
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        mAdapter = new ListingAdapter(thisObject, mockListings);
                        listView.setAdapter(mAdapter);
                    }
                },
                750);


//        mAdapter.notifyDataSetChanged();

    }

    public void addDefaultListings() {
        Listing one = new Listing("12345 Yeet St.", 12.0, 3, "Desc.");
        Listing two = new Listing("12345 Yeet St.", 100.0, 3, "Desc.");
        Listing three = new Listing("12345 Yeet St.", 100.0, 3, "Desc.");
        mockListings.add(one);
        mockListings.add(two);
        mockListings.add(three);
        mockListings.add(three);
//        mockListings.add(new Listing("12345 Yeet St.", 100.0, 3, "Desc."));
//        mockListings.add(new Listing("12345 Yeet St.", 100.0, 3, "Desc."));
    }

    public void addNewListings() {
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("listings");

        ref.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to Firebase
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
//                System.out.println(snapshot.toString());
                Map<String, Object> attrs = (Map<String, Object>) snapshot.getValue();
//                mockListings.addAll(listings.values());

                String address = attrs.get("address").toString();
                double price = Double.parseDouble(attrs.get("price").toString());
                int numRooms = Integer.parseInt(attrs.get("numRooms").toString());
                String description = attrs.get("description").toString();
                Listing newListing = new Listing(address, price, numRooms, description);
                Listing three = new Listing("12345 Yeet St.", 100.0, 3, "Desc.");
                System.out.println(address + ' ' + description);
                mockListings.add(newListing);
                mockListings.add(three);
                System.out.println("asdfasdafsdf");
                System.out.println(mockListings.size());
//                System.out.println("Author: " + newPost.get("author"));
//                System.out.println("Title: " + newPost.get("title"));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
            //... ChildEventListener also defines onChildChanged, onChildRemoved,
            //    onChildMoved and onCanceled, covered in later sections.
        });
    }


//    TODO: an Adapter class and create an instance of it
//    https://developer.android.com/reference/android/widget/ListView
//    https://developer.android.com/guide/topics/ui/declaring-layout.html#FillingTheLayout

//    Listing
//    class Listing {
//        String name;
//        String address;
//        double price;
//        int numRooms;
//        String description;
//
//        Listing(String name, String address, double price, int numRooms, String description) {
//            this.name = name;
//            this.address = address;
//            this.price = price;
//            this.numRooms = numRooms;
//            this.description = description;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public double getPrice() {
//            return price;
//        }
//
//        public int getNumRooms() {
//            return numRooms;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//    }
}
