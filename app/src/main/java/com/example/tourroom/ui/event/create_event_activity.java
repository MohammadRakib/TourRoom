package com.example.tourroom.ui.event;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tourroom.R;
import com.example.tourroom.ui.date_time_picker.date_picker_dialog_fragment;
import com.example.tourroom.ui.date_time_picker.time_picker_dialog_fragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Objects;

public class create_event_activity extends AppCompatActivity {

    TextInputLayout eventname_editText_forevent,eventdate_editText_forevent,journeystart_editText_forevent;
    TextInputEditText journey_edit, event_date_edit, event_name_edit;
    AppCompatButton uploadimage_forcreateevent;
    Button createbutton_forevent;
    ImageView event_image;
    public static create_event_view_model create_event_view_model_ob;
    Uri event_image_Uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_activityt);

        createbutton_forevent=findViewById(R.id.createbuttonforevent);
        eventname_editText_forevent=findViewById(R.id.eventnameeditText_layout);
        eventdate_editText_forevent=findViewById(R.id.eventdateeditText_layout);
        journeystart_editText_forevent=findViewById(R.id.journeystarteditText_layout);
        uploadimage_forcreateevent=findViewById(R.id.uploadimageforcreateevent);
        journey_edit = findViewById(R.id.journey_edit_text);
        event_date_edit = findViewById(R.id.event_date_edit_text);
        event_name_edit = findViewById(R.id.event_name_edit_text);
        event_image = findViewById(R.id.event_image);
        create_event_view_model_ob = new ViewModelProvider(this).get(create_event_view_model.class);

        createbutton_forevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        journey_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new time_picker_dialog_fragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        event_date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new date_picker_dialog_fragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        create_event_view_model_ob.getDate_format().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
              event_date_edit.setText(s);
            }
        });

        create_event_view_model_ob.getTime_format().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                journey_edit.setText(s);
            }
        });

        create_event_view_model_ob.getEvent_name().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                event_name_edit.setText(s);
            }
        });

        create_event_view_model_ob.getImage_uri().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                event_image.setImageURI(uri);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        create_event_view_model_ob.setEvent_name(Objects.requireNonNull(event_name_edit.getText()).toString());
    }

    public void upload_image(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                event_image_Uri = result.getUri();
                create_event_view_model_ob.setImage_uri(event_image_Uri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        event_image.setImageURI(event_image_Uri);
    }
}
