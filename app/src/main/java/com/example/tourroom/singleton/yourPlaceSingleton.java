package com.example.tourroom.singleton;

import com.example.tourroom.Data.place_data;

import java.util.ArrayList;
import java.util.List;

public class yourPlaceSingleton {

    private static volatile yourPlaceSingleton yourPlaceListInstance = null;
    List<place_data> yourPlaceList;

    public yourPlaceSingleton() {
        yourPlaceList = new ArrayList<>();
    }

    public static yourPlaceSingleton getYourPlaceListInstance(){
        if(yourPlaceListInstance == null){
            synchronized ((yourPlaceSingleton.class)){
                if (yourPlaceListInstance == null){
                    yourPlaceListInstance = new yourPlaceSingleton();
                }
            }
        }
        return yourPlaceListInstance;
    }

    public List<place_data> getYourPlaceList() {

        return yourPlaceList;
    }
}
