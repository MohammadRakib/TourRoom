package com.example.tourroom.ui.feed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tourroom.Data.postdata;
import com.example.tourroom.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class addNewPostFragment extends Fragment {

    ImageView addPostImage;
    AppCompatImageButton uploadPostImage;
    Button addPostButton;
    private Uri upLoadPost_image_uri;
    ProgressDialog progressDialog;
    StorageReference poststorageReference;
    private String currentUserID;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_post, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addPostImage = view.findViewById(R.id.postImageUpload);
        uploadPostImage = view.findViewById(R.id.uploadPostImage);
        addPostButton = view.findViewById(R.id.addNewPostButton);

        progressDialog=new ProgressDialog(requireActivity());
        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
        navController = Navigation.findNavController(requireActivity(),R.id.after_login_host_fragment);

        poststorageReference= FirebaseStorage.getInstance().getReference("PostImage");

        uploadPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = CropImage.activity()  //opening crop image activity for choosing image from gallery and cropping, this activity is from a custom api library
                        .getIntent(requireContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
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
                upLoadPost_image_uri = result.getUri();
                addPostImage.setImageURI(upLoadPost_image_uri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void uploadImage() {

        final String postKey = getINSTANCE().getRootRef().child("post").child(currentUserID).push().getKey();
        if (upLoadPost_image_uri != null) {

            progressDialog.setTitle("Posting");
            progressDialog.setMessage("Please wait,adding your post");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            final StorageReference storageReference = poststorageReference.child(currentUserID).child(postKey + ".jpg");

            UploadTask uploadTask = storageReference.putFile(upLoadPost_image_uri);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Could not Update Image", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        throw Objects.requireNonNull(task.getException());
                    }

                    // Continue with the task to get the download URL
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (task.isSuccessful()) {
                        final Uri downloadUri = task.getResult();
                        assert downloadUri != null;
                        postdata postdata = new postdata(postKey, downloadUri.toString(), "0", "0",currentUserID);
                        assert postKey != null;
                        getINSTANCE().getRootRef().child("post").child(currentUserID).child(postKey).setValue(postdata).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                Toast.makeText(requireActivity(), "Post added successfully", Toast.LENGTH_SHORT).show();
                                navController.popBackStack();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                String message = Objects.requireNonNull(e.toString());
                                Toast.makeText(requireActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        });


                    } else {
                        Toast.makeText(requireActivity(), "Could not  add post, try again", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }


                }
            });
        }
    }

}