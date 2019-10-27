package com.example.android.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListingAdapter extends ArrayAdapter<Listing> {
    private Context mContext;
    private List<Listing> listings = new ArrayList<>();

    public ListingAdapter(@NonNull Context context, @NonNull List<Listing> list) {
        super(context, 0, list);
        mContext = context;
        listings = list;
        System.out.println(list.size() + "IDENTIFY");
    }

    @Override
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.listing_item,parent,false);
        }

        Listing currentListing = listings.get(position);

        TextView address = (TextView) listItem.findViewById(R.id.listing_address);
        address.setText(currentListing.getAddress());

        TextView price = (TextView) listItem.findViewById(R.id.listing_price);
        price.setText(Double.toString(currentListing.getPrice()));

        TextView numRooms = (TextView) listItem.findViewById(R.id.listing_num_rooms);
        numRooms.setText(Integer.toString(currentListing.getNumRooms()));

        TextView description = (TextView) listItem.findViewById(R.id.listing_description);
        description.setText(currentListing.getDescription());

        return listItem;
    }
}
