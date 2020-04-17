package com.example.tourroom.ui.group;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.tourroom.R;



public class create_group_fragment extends Fragment {

    private Create_Group_Fragment_ViewModel mViewModel;

    public static create_group_fragment newInstance() {
        return new create_group_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_group_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Create_Group_Fragment_ViewModel.class);
        // TODO: Use the ViewModel
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText groupnameinput_edittext,groupdescription_edittext;
        Button newgroupcreate_button;
        ImageView  uploadgroupphoto_imageview;


        groupnameinput_edittext=view.findViewById(R.id.groupnameinputedittext);
        groupdescription_edittext=view.findViewById(R.id.groupdescriptionedittext);
        newgroupcreate_button=view.findViewById(R.id.newgroupcreatebutton);
        uploadgroupphoto_imageview=view.findViewById(R.id.uploadgroupphotoimageview);

        newgroupcreate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
