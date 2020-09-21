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

import com.example.tourroom.Data.announcement_data;
import com.example.tourroom.Data.place_data;
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

public class announcement_fragment extends Fragment {

    private AnnouncementFragmentViewModel mViewModel;
    RecyclerView recyclerview_forannouncement;
    RecyclerAdapterForAnnouncement recyclerAdapterForAnnouncement;
    int position;
    private String groupId,currentUser;
    private group_host_activity group_host_activity;
    List<announcement_data> announcementDataList;

    public static announcement_fragment newInstance() {
        return new announcement_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AnnouncementFragmentViewModel.class);
        return inflater.inflate(R.layout.announcement_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        announcementDataList=new ArrayList<>();


        group_host_activity = (group_host_activity) requireActivity();
        position = group_host_activity.getPosition();
        groupId = getYourGroupListInstance().getYourGroupList().get(position).getGroupId();
        currentUser = requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();




        recyclerview_forannouncement=view.findViewById(R.id.recycleviewforannouncementid);
        final FloatingActionButton actionbutton_forannouncement= view.findViewById(R.id.create_announcement);
        recyclerAdapterForAnnouncement=new RecyclerAdapterForAnnouncement(requireActivity(),announcementDataList);
        recyclerview_forannouncement.setAdapter(recyclerAdapterForAnnouncement);
        loadAnnouncements();



        recyclerview_forannouncement.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {
                    actionbutton_forannouncement.show();

                } else if (dy > 0) {
                    actionbutton_forannouncement.hide();
                }
            }
        });


        actionbutton_forannouncement.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), create_announcement_activity.class);
                intent.putExtra("key1",groupId);
                intent.putExtra("key2",currentUser);
                startActivity(intent);
                requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void loadAnnouncements() {
        getINSTANCE().getRootRef().child("Announcement").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    final announcement_data announcementData= data.getValue(announcement_data.class);

                    announcementDataList.add(announcementData);
                    recyclerAdapterForAnnouncement.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
