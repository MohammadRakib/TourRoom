package com.example.tourroom.ui.announcement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tourroom.R;

public class create_announcement_activity extends AppCompatActivity {

    EditText edittext_fornewannouncement;
    Button createnew_forannouncement;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_announcement_activity);

        edittext_fornewannouncement=findViewById(R.id.edittextfornewannouncement);
        createnew_forannouncement=findViewById(R.id.createnewannouncement);

        createnew_forannouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
