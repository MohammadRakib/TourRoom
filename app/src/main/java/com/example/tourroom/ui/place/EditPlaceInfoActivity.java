package com.example.tourroom.ui.place;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.MutableLiveData;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tourroom.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Objects;

public class EditPlaceInfoActivity extends AppCompatActivity {
    private EditText placedescription;
    private ImageView imageViewFromGallary;
    private AppCompatButton uploadFromGalleryButton;
    private Button updateToDatabaseBtn;
    Uri tour_place_image_uri;
    String useId,placeDes,ImageDownloadUrl;
    private boolean track=false;
    private ProgressDialog loadingBar;
    private StorageReference ImageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_place_info);

        placedescription = findViewById(R.id.placedescriptionEdit);
        imageViewFromGallary = findViewById(R.id.imageViewForUpload);
        uploadFromGalleryButton = findViewById(R.id.upload_image_from_gallery);
        updateToDatabaseBtn = findViewById(R.id.updateButton);

        loadingBar = new ProgressDialog(this);
        ImageReference = FirebaseStorage.getInstance().getReference("PlaceImage");

        uploadFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                track=true;
                Intent intent = CropImage.activity()
                        .getIntent(getApplicationContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            placedescription.setText(extras.getString("messagefromPlaceInfoActivity"));
            useId = extras.getString("id");
        }

        updateToDatabaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeDes=placedescription.getText().toString().toLowerCase();
                loadingBar.setTitle("Loading");
                loadingBar.setMessage("Update your data");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                if(!placeDes.equals("") && track==false)
                {
                    final HashMap<String, Object> map = new HashMap<>();
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("Places").child(useId);
                    placeDes=placedescription.getText().toString();
                    if(placeDes!=null )
                    {
                        map.put("placeDescription",placeDes);
                        ref.updateChildren(map);
                        Toast.makeText(getApplicationContext(), "Place description Updated Successfully", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        finish();
                    }
                }

                else if(!placeDes.equals("") && track==true)
                {
                    final StorageReference filePath = ImageReference.child(useId+".jpg");
                    UploadTask uploadTask =filePath.putFile(tour_place_image_uri);
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
                                ImageDownloadUrl=downloadUri.toString();
                                final HashMap<String, Object> map = new HashMap<>();
                                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference ref = database.getReference("Places").child(useId);
                                map.put("placeImage",ImageDownloadUrl);
                                map.put("placeDescription",placeDes);
                                ref.updateChildren(map);
                                Toast.makeText(EditPlaceInfoActivity.this, "place Photo and description updated successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditPlaceInfoActivity.this, "Database connection problem", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

                else if(placeDes.equals("") && track==true)
                {
                    final StorageReference filePath = ImageReference.child(useId+".jpg");
                    UploadTask uploadTask =filePath.putFile(tour_place_image_uri);
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
                                ImageDownloadUrl=downloadUri.toString();
                                final HashMap<String, Object> map = new HashMap<>();
                                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference ref = database.getReference("Places").child(useId);
                                map.put("placeImage",ImageDownloadUrl);
                                map.put("placeDescription",placeDes);
                                ref.updateChildren(map);
                                Toast.makeText(EditPlaceInfoActivity.this, "place Photo and description updated successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditPlaceInfoActivity.this, "Database connection problem", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    loadingBar.dismiss();
                    Toast.makeText(EditPlaceInfoActivity.this, "Please Update something", Toast.LENGTH_SHORT).show();

                }
                track=false;
                placedescription.setText("");
                imageViewFromGallary.setImageResource(R.drawable.dummyimage);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {  //requesting for pick image from gallery
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                tour_place_image_uri = result.getUri();
                setImage_uri(tour_place_image_uri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        imageViewFromGallary.setImageURI(tour_place_image_uri);
    }

    private MutableLiveData<Uri> image_uri;

    public EditPlaceInfoActivity()
    {
        image_uri = new MutableLiveData<>();
    }



    MutableLiveData<Uri> getImage_uri() {
        return image_uri;
    }

    void setImage_uri(Uri image_uri) {
        this.image_uri.setValue(image_uri);
    }

}