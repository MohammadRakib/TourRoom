package com.example.tourroom.ui.group;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tourroom.R;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class create_group_fragment extends Fragment {

    private Create_Group_Fragment_ViewModel create_group_fragment_viewModel_ob;
    Uri group_image_uri;
    TextInputEditText groupnameinput_edittext,groupdescription_edittext;
    Button newgroupcreate_button;
    AppCompatButton uploadgroupphoto;
    ImageView group_image;

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
        uploadgroupphoto=view.findViewById(R.id.upload_image_for_create_group);
        group_image = view.findViewById(R.id.group_image);
        create_group_fragment_viewModel_ob = new ViewModelProvider(this).get(Create_Group_Fragment_ViewModel.class);

        newgroupcreate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        uploadgroupphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = CropImage.activity()  //opening crop image activity for choosing image from gallery and cropping, this activity is from a custom api library
                        .getIntent(requireContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });


        create_group_fragment_viewModel_ob.getImage_uri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                group_image.setImageURI(uri);
            }
        });
        groupnameinput_edittext.addTextChangedListener(textWatcher);
        groupdescription_edittext.addTextChangedListener(textWatcher);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {  //requesting for pick image from gallery
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                group_image_uri = result.getUri();
                create_group_fragment_viewModel_ob.setImage_uri(group_image_uri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                assert result != null;
                Exception error = result.getError();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        group_image.setImageURI(group_image_uri);
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
