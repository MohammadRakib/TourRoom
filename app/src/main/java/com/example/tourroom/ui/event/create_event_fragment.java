package com.example.tourroom.ui.event;

import androidx.annotation.RequiresApi;
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

public class create_event_fragment extends Fragment {

    private CreateEventFragmentViewModel mViewModel;

    public static create_event_fragment newInstance() {
        return new create_event_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_event_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreateEventFragmentViewModel.class);
        // TODO: Use the ViewModel
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText eventname_editText_forevent,eventdate_editText_forevent,eventday_editText_forevent,journeystart_editText_forevent;
        ImageView uploadimage_forcreateevent;
        Button createbutton_forevent;



        createbutton_forevent=view.findViewById(R.id.createbuttonforevent);
        eventname_editText_forevent=view.findViewById(R.id.eventnameeditText);
        eventdate_editText_forevent=view.findViewById(R.id.eventdateeditText);
        eventday_editText_forevent=view.findViewById(R.id.eventdayeditText);
        journeystart_editText_forevent=view.findViewById(R.id.journeystarteditText);
        uploadimage_forcreateevent=view.findViewById(R.id.uploadimageforcreateevent);

        createbutton_forevent.setOnClickListener(new View.OnClickListener() {
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
