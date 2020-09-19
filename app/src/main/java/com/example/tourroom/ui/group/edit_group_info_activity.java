package com.example.tourroom.ui.group;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.group_data;
import com.example.tourroom.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;

public class edit_group_info_activity extends AppCompatActivity {

    ImageView groupImage;
    TextView grpName, grpDescription;
    AppCompatImageButton upload;
    private Uri group_image_uri;
    Context context;
    private int position;
    private String groupId;
    private StorageReference groupImagesRef;
    private ProgressDialog loadingBar;
    Query groupLoadQuery;
    ValueEventListener groupLoadListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_group_info_activity);

        groupImage = findViewById(R.id.editgroupinfo_imageview);
        grpName = findViewById(R.id.editgroupinfo_groupName_textview);
        grpDescription = findViewById(R.id.discriptionextand_editgroupinfo_textview);
        upload = findViewById(R.id.editGroupImage);
        groupImagesRef = FirebaseStorage.getInstance().getReference("GroupImage");
        context = this;
        loadingBar = new ProgressDialog(this);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CropImage.activity()  //opening crop image activity for choosing image from gallery and cropping, this activity is from a custom api library
                        .getIntent(context);
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //for shared activity animation
            position = extras.getInt("position");
            groupId = getYourGroupListInstance().getYourGroupList().get(position).getGroupId();
            loadGroupInfo();

        }

    }

    private void loadGroupInfo() {

        groupLoadQuery = getINSTANCE().getRootRef().child("GROUP").child(groupId);
        groupLoadListener = groupLoadQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                group_data group_data = snapshot.getValue(group_data.class);
                assert group_data != null;
                grpName.setText(group_data.getGroupName());
                grpDescription.setText(group_data.getGroupDescription());

                Glide.with(context)
                        .load(group_data.getGroupImage())
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .dontAnimate()
                        .placeholder(R.drawable.dummyimage)
                        .into(groupImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {  //requesting for pick image from gallery
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                group_image_uri = result.getUri();
                uploadGroupImage();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadGroupImage() {
        loadingBar.setTitle("Updating");
        loadingBar.setMessage("Please wait, Updating your group image...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        final StorageReference filePath = groupImagesRef.child(groupId+".jpg");
        UploadTask uploadTask =filePath.putFile(group_image_uri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }

                // Continue with the task to get the download URL
                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    final Uri group_image_download_Uri = task.getResult();
                    assert group_image_download_Uri != null;

                    getINSTANCE().getRootRef().child("GROUP").child(groupId).child("groupImage")
                            .setValue(group_image_download_Uri.toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        loadingBar.dismiss();
                                        Toast.makeText(context, "Group image updated Successfully", Toast.LENGTH_SHORT).show();
                                        getYourGroupListInstance().getYourGroupList().get(position).setGroupImage(group_image_download_Uri.toString());
                                    }else {
                                        loadingBar.dismiss();
                                        String message = Objects.requireNonNull(task.getException()).toString();
                                        Toast.makeText(context, "Error: " + message, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                } else {

                    Toast.makeText(context, "could not update image, try again", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        groupLoadQuery.removeEventListener(groupLoadListener);
    }
}
