package com.example.tourroom.singleton;



import com.example.tourroom.Data.yourGroupData;

import java.util.ArrayList;
import java.util.List;

public final class yourGroupSingleton {

    private static volatile yourGroupSingleton yourGroupListInstance = null;
    List<yourGroupData> yourGroupList;

    private yourGroupSingleton() {

        yourGroupList = new ArrayList<>();

    }

    public static yourGroupSingleton getYourGroupListInstance(){
        if(yourGroupListInstance == null){
            synchronized ((yourGroupSingleton.class)){
                if (yourGroupListInstance == null){
                    yourGroupListInstance = new yourGroupSingleton();
                }
            }
        }
        return yourGroupListInstance;
    }

    public List<yourGroupData> getYourGroupList() {
        return yourGroupList;
    }
}
