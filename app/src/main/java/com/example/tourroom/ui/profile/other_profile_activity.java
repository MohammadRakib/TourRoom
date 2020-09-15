package com.example.tourroom.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tourroom.R;

public class other_profile_activity extends AppCompatActivity {
    RecyclerView otherprofile_recycler_view;
    Otherprofile_Recycler_Adapter otherprofile_recycler_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_profile_activity);

        otherprofile_recycler_view=findViewById(R.id.otherprofile_recyclerview);
        otherprofile_recycler_adapter=new Otherprofile_Recycler_Adapter();

        otherprofile_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        otherprofile_recycler_view.setAdapter(otherprofile_recycler_adapter);

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        otherprofile_recycler_view.addItemDecoration(dividerItemDecoration);
    }
}
