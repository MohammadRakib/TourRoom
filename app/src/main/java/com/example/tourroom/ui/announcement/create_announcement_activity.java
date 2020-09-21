package com.example.tourroom.ui.announcement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourroom.Data.announcement_data;
import com.example.tourroom.R;
import com.example.tourroom.ui.group.group_host_activity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;
import static java.util.Objects.requireNonNull;

public class create_announcement_activity extends AppCompatActivity {

    EditText edittext_fornewannouncement;
    Button createnew_forannouncement;
    String userId,groupId;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_announcement_activity);

        edittext_fornewannouncement=findViewById(R.id.edittextfornewannouncement);
        createnew_forannouncement=findViewById(R.id.createnewannouncement);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            groupId=bundle.getString("key1");
            userId=bundle.getString("key2");
        }




        createnew_forannouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                createAnnouncements();

            }
        });
        edittext_fornewannouncement.addTextChangedListener(textWatcher);

    }

    private void createAnnouncements() {
        final String date_time = String.valueOf(System.currentTimeMillis()/1000);
        String message,announcementId;
        message=edittext_fornewannouncement.getText().toString();
        announcementId= getINSTANCE().getRootRef().child("Announcement").push().getKey();
        announcement_data announcementData=new announcement_data(announcementId,message,userId,date_time);
        assert announcementId!=null;
        getINSTANCE().getRootRef().child("Announcement").child(groupId).child(announcementId).
                setValue(announcementData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(create_announcement_activity.this, "Announcement created", Toast.LENGTH_SHORT).show();

                edittext_fornewannouncement.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(create_announcement_activity.this, "Failure Occured", Toast.LENGTH_SHORT).show();

                edittext_fornewannouncement.setText("");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String announcement = Objects.requireNonNull(edittext_fornewannouncement.getText()).toString().trim();
            createnew_forannouncement.setEnabled(!announcement.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
