package com.example.tourroom.ui.group;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tourroom.Data.group_data;
import com.example.tourroom.Data.yourGroupData;
import com.example.tourroom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.example.tourroom.After_login_Activity.dummyGroupImage;
import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;


public class create_group_fragment extends Fragment {

    TextInputEditText groupnameinput_edittext,groupdescription_edittext;
    Button newgroupcreate_button;

    private String currentUser;
    private ProgressDialog loadingBar;
    private NavController navController;

    public static create_group_fragment newInstance() {
        return new create_group_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_group_fragment, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        groupnameinput_edittext=view.findViewById(R.id.group_name_edit_text);
        groupdescription_edittext=view.findViewById(R.id.group_description_edit_text);
        newgroupcreate_button=view.findViewById(R.id.newgroupcreatebutton);
        navController = Navigation.findNavController(requireActivity(),R.id.after_login_host_fragment);

        loadingBar = new ProgressDialog(getContext());

        currentUser = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();

        newgroupcreate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAnewGroup();
            }
        });


        groupnameinput_edittext.addTextChangedListener(textWatcher);
        groupdescription_edittext.addTextChangedListener(textWatcher);
    }

    //watch edit text if it is empty or not
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          String group_name = Objects.requireNonNull(groupnameinput_edittext.getText()).toString().trim();
          String group_description = Objects.requireNonNull(groupdescription_edittext.getText()).toString().trim();
          newgroupcreate_button.setEnabled(!group_name.isEmpty() && !group_description.isEmpty());  //setting the button enable if all edit text are not empty
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createAnewGroup(){

        final String group_name, group_description, group_id;

        loadingBar.setTitle("Create Group");
        loadingBar.setMessage("Please wait, your group is creating...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        group_name = Objects.requireNonNull(groupnameinput_edittext.getText()).toString().trim();
        group_description = Objects.requireNonNull(groupdescription_edittext.getText()).toString().trim();
        group_id = getINSTANCE().getRootRef().child("GROUP").push().getKey();
        // localGroupId = group_id;
        group_data groupData = new group_data(group_id,group_name,dummyGroupImage,group_description,currentUser,"0");

        final Map<String, Object> update = new HashMap<>(); //this hashmap is used to write different data in different path in the database at once or atomically
        update.put("GROUP/"+group_id+"/members/"+currentUser,true);
        update.put("Users/"+currentUser+"/joinedGroups/"+group_id+"/msgCountUser","0");

        assert group_id != null;
        getINSTANCE().getRootRef().child("GROUP").child(group_id).setValue(groupData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        getINSTANCE().getRootRef().updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                loadingBar.dismiss();
                                //update newly created group in the list
                                yourGroupData groupData = new yourGroupData(group_id,group_name,"0","0");
                                getYourGroupListInstance().getYourGroupList().add(0,groupData);

                                Toast.makeText(getContext(), "Group Created Successfully", Toast.LENGTH_SHORT).show();
                                navController.popBackStack();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Error "+e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error "+e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
