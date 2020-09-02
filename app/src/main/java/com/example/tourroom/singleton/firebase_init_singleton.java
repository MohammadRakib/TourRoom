package com.example.tourroom.singleton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class firebase_init_singleton {
    private static volatile firebase_init_singleton INSTANCE = null;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private firebase_init_singleton(){
        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
    }

    public static firebase_init_singleton getINSTANCE(){
        if(INSTANCE == null){
            synchronized ((firebase_init_singleton.class)){
                if (INSTANCE == null){
                    INSTANCE = new firebase_init_singleton();
                }
            }
        }
        return INSTANCE;
    }

    public FirebaseAuth getMAuth() {
        return mAuth;
    }

    public DatabaseReference getRootRef() {
        return RootRef;
    }
}
