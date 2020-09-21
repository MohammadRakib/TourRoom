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

import com.example.tourroom.Data.announcement_data;
import com.example.tourroom.Data.event_data;
import com.example.tourroom.R;
import com.example.tourroom.ui.group.group_host_activity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;
import static java.util.Objects.requireNonNull;

public class Event_fragment extends Fragment {

    private EventFragmentViewModel mViewModel;
    RecyclerView recyclerview_forevent;
    RecyclerAdapterForEvent recyclerAdapterforevent;
    int position;
    private String groupId;
    group_host_activity group_host_activity;
    String currentUser;
    List<event_data> eventDataList;

    public static Event_fragment newInstance() {
        return new Event_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(EventFragmentViewModel.class);
        return inflater.inflate(R.layout.event_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        eventDataList=new ArrayList<>();


        group_host_activity = (group_host_activity) requireActivity();
        position = group_host_activity.getPosition();
        groupId = getYourGroupListInstance().getYourGroupList().get(position).getGroupId();
        currentUser = requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();


        recyclerview_forevent=view.findViewById(R.id.recycleviewidforevent);
        final FloatingActionButton actionbutton_forevent= view.findViewById(R.id.create_eventfloatingactionbutton);
        recyclerAdapterforevent=new RecyclerAdapterForEvent(requireActivity(),eventDataList);
        recyclerview_forevent.setAdapter(recyclerAdapterforevent);
        loadEventDetails();
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
                intent.putExtra("key3",groupId);
                intent.putExtra("key4",currentUser);
                startActivity(intent);
                requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void loadEventDetails() {
        getINSTANCE().getRootRef().child("Event").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    final event_data eventData= data.getValue(event_data.class);

                    eventDataList.add(eventData);
                    recyclerAdapterforevent.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
