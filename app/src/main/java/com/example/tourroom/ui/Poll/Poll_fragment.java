package com.example.tourroom.ui.Poll;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tourroom.Data.Read_poll_data;
import com.example.tourroom.Data.poll_data;
import com.example.tourroom.R;
import com.example.tourroom.ui.group.group_host_activity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;

public class Poll_fragment extends Fragment {

    private PollFragmentViewModel mViewModel;
    RecyclerView recyclerview_forcreatingpoll;
    RecyclerAdapterForCreatePoll recyclerAdapterForCreatePoll;
    FloatingActionButton actionbutton_forpoll;
    private String groupId;
    group_host_activity group_host_activity;
    int position;
    List<Read_poll_data> pollDataList;

    public static Poll_fragment newInstance() {
        return new Poll_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PollFragmentViewModel.class);
        return inflater.inflate(R.layout.poll_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview_forcreatingpoll=view.findViewById(R.id.recyclerviewidforcreatingpoll);
        actionbutton_forpoll= view.findViewById(R.id.create_pollfloatingbutton);


        pollDataList=new ArrayList<>();
        group_host_activity = (group_host_activity) requireActivity();
        position = group_host_activity.getPosition();
        groupId = getYourGroupListInstance().getYourGroupList().get(position).getGroupId();







        recyclerAdapterForCreatePoll=new  RecyclerAdapterForCreatePoll(pollDataList,requireActivity());

        recyclerview_forcreatingpoll.setAdapter(recyclerAdapterForCreatePoll);
        //loadPollList();

        recyclerview_forcreatingpoll.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {
                    actionbutton_forpoll.show();

                } else if (dy > 0) {
                    actionbutton_forpoll.hide();
                }
            }
        });



        actionbutton_forpoll.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(getActivity(), create_poll_activity.class);
                intent.putExtra("key100",groupId);
                startActivity(intent);*/
                requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }

/*    private void loadPollList() {

        getINSTANCE().getRootRef().child("Poll").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren())
                {
                    Read_poll_data readPollData=new Read_poll_data();
                    String f=data.child("Option1").child("optionName").getValue().toString();
                    readPollData.setOptionName1(f);
                    Log.d("onactvity", "onDataChange: "+f);
                    readPollData.setOption1Vote(data.child("Option1").child("vote").getValue().toString());
                    readPollData.setOptionName2(data.child("Option2").child("optionName").getValue().toString());
                    readPollData.setOption2Vote(data.child("Option2").child("vote").getValue().toString());
                    pollDataList.add(readPollData);
                    recyclerAdapterForCreatePoll.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            }*/


    }


