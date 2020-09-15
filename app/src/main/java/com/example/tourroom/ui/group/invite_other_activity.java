package com.example.tourroom.ui.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tourroom.R;

public class invite_other_activity extends AppCompatActivity {

    RecyclerView inviteOtherRecycleView;
    inviteOthersAdapter inviteOthersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_other_activity);

        inviteOtherRecycleView = findViewById(R.id.inviteOtherRecycleView);
        inviteOthersAdapter = new inviteOthersAdapter();
        inviteOtherRecycleView.setAdapter(inviteOthersAdapter);

    }
}
