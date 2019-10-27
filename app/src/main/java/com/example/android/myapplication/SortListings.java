package com.example.android.myapplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortListings {
    protected List<Listing> sort(List<Listing> list, int price, int numrooms){
        List<Listing> rList = new ArrayList();
        for (Listing l: list) {
            if (l.getPrice() <= price && l.getNumRooms() == numrooms) {
                rList.add(l);
            }
        }
        Collections.sort(rList, new Comparator<Listing>() {
            @Override
            public int compare(Listing o1, Listing o2) {
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
