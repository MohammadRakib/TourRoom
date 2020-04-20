package com.example.tourroom.ui.Poll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tourroom.R;

public class create_poll_activity extends AppCompatActivity {

    Button createnewpollbutton;
    ImageView addpolloption;
    TextView option1textview_forpoll,option2textview_forpoll;
    EditText option1_edittext_forpoll,option2_edittext_forpoll;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_poll_activity);

        addpolloption=findViewById(R.id.addpolloption_imageview);
        option1textview_forpoll=findViewById(R.id.option1textview);
        option2textview_forpoll=findViewById(R.id.option2textview);
        option1_edittext_forpoll=findViewById(R.id.option1_edittext_forcreatingpoll);
        option2_edittext_forpoll=findViewById(R.id.option2_edittext_forcreatingpoll);
        createnewpollbutton=findViewById(R.id.create_newpollbutton);
        createnewpollbutton.setOnClickListener(new View.OnClickListener() {
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
