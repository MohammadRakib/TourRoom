package com.example.tourroom.ui.event;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tourroom.R;

public class create_event_activity extends AppCompatActivity {

    EditText eventname_editText_forevent,eventdate_editText_forevent,eventday_editText_forevent,journeystart_editText_forevent;
    ImageView uploadimage_forcreateevent;
    Button createbutton_forevent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_activityt);

        createbutton_forevent=findViewById(R.id.createbuttonforevent);
        eventname_editText_forevent=findViewById(R.id.eventnameeditText);
        eventdate_editText_forevent=findViewById(R.id.eventdateeditText);
        eventday_editText_forevent=findViewById(R.id.eventdayeditText);
        journeystart_editText_forevent=findViewById(R.id.journeystarteditText);
        uploadimage_forcreateevent=findViewById(R.id.uploadimageforcreateevent);

        createbutton_forevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
