package com.example.tourroom;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class After_login_Activity extends AppCompatActivity {

    public static String UserName, UserEmail, UserImage;
    private String currentUserID;
    private ProgressDialog loadingBar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_login);

        loadingBar = new ProgressDialog(this);

        //getting current login user
        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
        loadUserProfile();

    }

    public void loadUserProfile(){

        loadingBar.setTitle("Loading");
        loadingBar.setMessage("Please wait, Loading your data");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        getINSTANCE().getRootRef().child("Users").child(currentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists() && dataSnapshot.hasChild("image")){
                            UserName = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                            UserEmail = Objects.requireNonNull(dataSnapshot.child("UEmail").getValue()).toString();
                            UserImage = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                            loadingBar.dismiss();

                        }else if(dataSnapshot.exists()) {
                            UserName = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                            UserEmail = Objects.requireNonNull(dataSnapshot.child("UEmail").getValue()).toString();
                            loadingBar.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

}
