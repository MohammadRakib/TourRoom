package com.example.tourroom.ui.event;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tourroom.Data.event_data;
import com.example.tourroom.R;
import com.example.tourroom.ui.date_time_picker.date_picker_dialog_fragment;
import com.example.tourroom.ui.date_time_picker.time_picker_dialog_fragment;
import com.example.tourroom.ui.place.EditPlaceInfoActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class create_event_activity extends AppCompatActivity {

    TextInputLayout eventname_editText_forevent,eventdate_editText_forevent,journeystart_editText_forevent;
    TextInputEditText journey_edit, event_date_edit, event_name_edit;
    AppCompatButton uploadimage_forcreateevent;
    Button createbutton_forevent;
    ImageView event_image;
    public static create_event_view_model create_event_view_model_ob;
    Uri event_image_Uri;
    String groupId,userId,ImageDownloadUrlForEvent,eventId;
    private StorageReference ImageReference;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_activityt);

        ImageReference = FirebaseStorage.getInstance().getReference("EventImage");
        loadingBar = new ProgressDialog(this);

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





        Bundle bundle1=getIntent().getExtras();
        if(bundle1!=null)
        {
            groupId=bundle1.getString("key3");
            userId=bundle1.getString("key4");
        }



        createbutton_forevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                if(event_image.getDrawable()==null)
                {
                    Toast.makeText(create_event_activity.this, "Please upload a event image to cotinue", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    createNewEvent();
                }
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


        create_event_view_model_ob.getImage_uri().observe(this, new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                event_image.setImageURI(uri);
            }
        });

        event_name_edit.addTextChangedListener(textWatcher);
        event_date_edit.addTextChangedListener(textWatcher);
        journey_edit.addTextChangedListener(textWatcher);


    }





    private void createNewEvent() {

        loadingBar.setTitle("Add New Event");
        loadingBar.setMessage("Please wait, your event is adding to database...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();




        final String dateTime = String.valueOf(System.currentTimeMillis()/1000);
        String eventName,eventDate,eventStartTime;
        eventName= event_name_edit.getText().toString();
        eventDate=event_date_edit.getText().toString();
        eventStartTime=journey_edit.getText().toString();
        eventId=getINSTANCE().getRootRef().child("Event").push().getKey();


        final StorageReference filePath = ImageReference.child(eventId+".jpg");
        UploadTask uploadTask =filePath.putFile(event_image_Uri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }


                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if (task.isSuccessful()) {
                    final Uri downloadUri = task.getResult();
                    assert downloadUri != null;
                    ImageDownloadUrlForEvent=downloadUri.toString();
                    final HashMap<String, Object> map = new HashMap<>();
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("Event").child(groupId).child(eventId);
                    map.put("eventPhoto",ImageDownloadUrlForEvent);
                    ref.updateChildren(map);
                    Toast.makeText(create_event_activity.this, "Event updated successfully", Toast.LENGTH_SHORT).show();
                    event_name_edit.setText("");
                    event_date_edit.setText("");
                    journey_edit.setText("");
                    event_image.setImageResource(R.drawable.dummyimage);
                    loadingBar.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(create_event_activity.this, "Problem occured", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        });


        event_data eventData=new event_data(eventId,eventName,eventDate,eventStartTime,userId,dateTime);
        getINSTANCE().getRootRef().child("Event").child(groupId).child(eventId).setValue(eventData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(create_event_activity.this, "Problem occured", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
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
    }

    public void upload_image(View view) { //upload image on_clicker
        CropImage.activity()  //opening crop image activity for choosing image from gallery and cropping, this activity is from a custom api library
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) { //requesting for pick image from gallery
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

    //watch edit text if it is empty or not
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String event_name = Objects.requireNonNull(event_name_edit.getText()).toString().trim();
            String event_date = Objects.requireNonNull(event_date_edit.getText()).toString().trim();
            String journey_start = Objects.requireNonNull(journey_edit.getText()).toString().trim();
            createbutton_forevent.setEnabled(!event_name.isEmpty() && !event_date.isEmpty() && !journey_start.isEmpty()); //setting the button enable if all edit text are not empty
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
