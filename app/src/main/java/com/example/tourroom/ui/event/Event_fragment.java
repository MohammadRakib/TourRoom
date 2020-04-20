package com.example.tourroom.ui.event;

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

public class Event_fragment extends Fragment {

    private EventFragmentViewModel mViewModel;
    RecyclerView recyclerview_forevent;
    RecyclerAdapterForEvent recyclerAdapterforevent;

    public static Event_fragment newInstance() {
        return new Event_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(EventFragmentViewModel.class);
        return inflater.inflate(R.layout.event_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview_forevent=view.findViewById(R.id.recycleviewidforevent);
        final FloatingActionButton actionbutton_forevent= view.findViewById(R.id.create_eventfloatingactionbutton);
        recyclerAdapterforevent=new RecyclerAdapterForEvent();
        recyclerview_forevent.setAdapter(recyclerAdapterforevent);
        recyclerview_forevent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {
                    actionbutton_forevent.show();

                } else if (dy > 0) {
                    actionbutton_forevent.hide();
                }

            }
        });




        actionbutton_forevent.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), create_event_activity.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
}
