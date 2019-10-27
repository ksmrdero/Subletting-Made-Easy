package com.example.android.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListingAdapter extends ArrayAdapter<MainActivity.Listing> {
    private Context mContext;
    private List<MainActivity.Listing> listings = new ArrayList<>();

    public ListingAdapter(@NonNull Context context, @NonNull List<MainActivity.Listing> list) {
        super(context, 0, list);
        mContext = context;
        listings = list;
    }

    @Override
    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.listing_item,parent,false);
        }

        MainActivity.Listing currentListing = listings.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.listing_name);
        name.setText(currentListing.getName());

        TextView address = (TextView) listItem.findViewById(R.id.listing_address);
        address.setText(currentListing.getAddress());

        TextView price = (TextView) listItem.findViewById(R.id.listing_price);
        price.setText(Double.toString(currentListing.getPrice()));

        TextView numRooms = (TextView) listItem.findViewById(R.id.listing_num_rooms);
        numRooms.setText(Integer.toString(currentListing.getNumRooms()));

        ImageView image = (ImageView) listItem.findViewById(R.id.listing_image);
        image.setImageDrawable(currentListing.getImage());

        return listItem;
    }
}
