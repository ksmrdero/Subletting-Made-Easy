package com.example.android.myapplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortListings {
    protected List<MainActivity.Listing> sort(List<MainActivity.Listing> list, int price, int numrooms){
        List<MainActivity.Listing> rList = new ArrayList();
        for (MainActivity.Listing l: list) {
            if (l.getPrice() <= price && l.getNumRooms() == numrooms) {
                rList.add(l);
            }
        }
        Collections.sort(rList, new Comparator<MainActivity.Listing>() {
            @Override
            public int compare(MainActivity.Listing o1, MainActivity.Listing o2) {
                if (o1.getPrice() > o2.getPrice()) {
                    return 1;
                } else if (o1.getPrice() == o2.getPrice()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        return rList;
    }
}
