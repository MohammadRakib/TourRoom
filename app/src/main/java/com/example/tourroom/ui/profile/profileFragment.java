package com.example.tourroom.ui.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.Data.User_Data;
import com.example.tourroom.Data.postdata;
import com.example.tourroom.R;
import com.example.tourroom.ui.feed.commentActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class profileFragment extends Fragment implements profileInterface {
    RecyclerView profile_recycler_view;
    Profile_Recycler_Adapter profile_recycler_adapter;
    private Uri profile_image_uri;
    private ProgressDialog loadingBar;
    private StorageReference ProfileImageReference;
    private String currentUserID;
    profileInterface profileInterface;
    Query profileLoadQuery;
    ValueEventListener profileLoadListener;
    List<postdata> userPostList;
    boolean postLoadComplete = false;


    private String UserName,UserImage;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
        ProfileImageReference = FirebaseStorage.getInstance().getReference("UserImage");
        profileInterface = this;
        userPostList = new ArrayList<>();
        loadingBar = new ProgressDialog(requireActivity());
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL);
        postLoadComplete = false;
        loadUserProfile(view, dividerItemDecoration, profileInterface);;


    }

    public void loadUserProfile(final View view, final DividerItemDecoration dividerItemDecoration, final profileInterface profileInterface){

        profileLoadQuery = getINSTANCE().getRootRef().child("Users").child(currentUserID);

       profileLoadListener = profileLoadQuery.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User_Data user_data = dataSnapshot.getValue(User_Data.class);
                        assert user_data != null;
                        UserName = user_data.getName();
                        UserImage = user_data.getImage();

                        profile_recycler_view=view.findViewById(R.id.profile_recyclerview);
                        profile_recycler_view.setLayoutManager(new LinearLayoutManager(requireActivity()));
                        profile_recycler_adapter=new Profile_Recycler_Adapter(requireActivity(),profileInterface,UserName,UserImage,userPostList);
                        profile_recycler_view.setAdapter(profile_recycler_adapter);
                        profile_recycler_view.addItemDecoration(dividerItemDecoration);

                        if(!postLoadComplete){
                            loadPost();
                            postLoadComplete = true;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadPost() {
        getINSTANCE().getRootRef().child("post").child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()){
                    postdata postdata = data.getValue(postdata.class);
                    userPostList.add(postdata);
                    profile_recycler_adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onclickEditImage() {

        Intent intent = CropImage.activity()  //opening crop image activity for choosing image from gallery and cropping, this activity is from a custom api library
                .getIntent(requireContext());
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);

    }

    @Override
    public void onclickComment(String postId, String userId) {
        Intent intent = new Intent(requireActivity(), commentActivity.class);
        intent.putExtra("postId",postId);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {  //requesting for pick image from gallery
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                profile_image_uri = result.getUri();
                updateProfileImage();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }
    }

    private void updateProfileImage() {

        loadingBar.setTitle("Updating");
        loadingBar.setMessage("Please wait, Updating your profile image...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        final StorageReference filePath = ProfileImageReference.child(currentUserID+".jpg");
        UploadTask uploadTask =filePath.putFile(profile_image_uri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Could not Update Image", Toast.LENGTH_SHORT).show();
                    throw Objects.requireNonNull(task.getException());
                }

                // Continue with the task to get the download URL
                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if (task.isSuccessful()) {
                    final Uri downloadUri = task.getResult();
                    assert downloadUri != null;
                    getINSTANCE().getRootRef().child("Users").child(currentUserID).child("image").setValue(downloadUri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            loadingBar.dismiss();
                            Toast.makeText(requireActivity(), "Image Updated Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingBar.dismiss();
                            String message = Objects.requireNonNull(e.toString());
                            Toast.makeText(requireActivity(), "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    });


                }else {
                    Toast.makeText(requireActivity(), "could not update image, try again", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }


            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        profileLoadQuery.removeEventListener(profileLoadListener);
    }


}
