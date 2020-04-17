package com.example.tourroom.ui.announcement;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tourroom.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class announcement_fragment extends Fragment {

    private AnnouncementFragmentViewModel mViewModel;
    RecyclerView recyclerview_forannouncement;
    RecyclerAdapterForAnnouncement recyclerAdapterForAnnouncement;

    public static announcement_fragment newInstance() {
        return new announcement_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AnnouncementFragmentViewModel.class);
        return inflater.inflate(R.layout.announcement_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerview_forannouncement=view.findViewById(R.id.recycleviewforannouncementid);
        recyclerAdapterForAnnouncement=new RecyclerAdapterForAnnouncement();

        recyclerview_forannouncement.setAdapter(recyclerAdapterForAnnouncement);
        FloatingActionButton actionbutton_forannouncement= view.findViewById(R.id.create_announcement);

        actionbutton_forannouncement.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), create_announcement_activity.class);
                startActivity(intent);
            }
        });
    }
}
