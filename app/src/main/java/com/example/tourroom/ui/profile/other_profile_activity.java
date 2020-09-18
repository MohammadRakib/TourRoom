package com.example.tourroom.ui.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tourroom.Data.User_Data;
import com.example.tourroom.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class other_profile_activity extends AppCompatActivity {
    RecyclerView otherprofile_recycler_view;
    Otherprofile_Recycler_Adapter otherprofile_recycler_adapter;
    int position;
    String memberId;
    private String UserName,UserImage;
    otherProfileInterface otherProfileInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_profile_activity);

        otherprofile_recycler_view=findViewById(R.id.otherprofile_recyclerview);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //for shared activity animation
            position = extras.getInt("position");
            memberId = extras.getString("memberId");
            otherProfileLoad();
        }
        otherprofile_recycler_adapter=new Otherprofile_Recycler_Adapter(this,UserName,UserImage);
        otherprofile_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        otherprofile_recycler_view.setAdapter(otherprofile_recycler_adapter);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        otherprofile_recycler_view.addItemDecoration(dividerItemDecoration);

    }

    private void otherProfileLoad() {
        getINSTANCE().getRootRef().child("Users").child(memberId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_Data user_data = snapshot.getValue(User_Data.class);
                assert user_data != null;
                UserName = user_data.getName();
                UserImage = user_data.getImage();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
