package com.example.tourroom.ui.member;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourroom.Data.User_Data;
import com.example.tourroom.R;
import com.example.tourroom.ui.group.group_host_activity;
import com.example.tourroom.ui.profile.other_profile_activity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;

public class Members_fragment extends Fragment implements memberInterface{

    private MembersFragmentViewModel mViewModel;
    private View view;
    @Nullable
    private Bundle savedInstanceState;
    RecyclerView memberRecycleView;
    memberAdapter memberAdapter;
    List<User_Data> groupMemberList;
    int position;
    private String groupId;
    private group_host_activity group_host_activity;
    TextView memcount;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(MembersFragmentViewModel.class);
        return inflater.inflate(R.layout.members_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        memberRecycleView = view.findViewById(R.id.member_recycleview);
        memcount = view.findViewById(R.id.membercount_textview);

        //getting data from activity
        group_host_activity = (group_host_activity) requireActivity();
        position = group_host_activity.getPosition();
        groupId = getYourGroupListInstance().getYourGroupList().get(position).getGroupId();
        
        groupMemberList = new ArrayList<>();
        memberAdapter = new memberAdapter(groupMemberList,requireActivity(),this);
        memberRecycleView.setAdapter(memberAdapter);
        loadGroupMembers();
    }

    private void loadGroupMembers() {

        getINSTANCE().getRootRef().child("GROUP").child(groupId).child("members").addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                getINSTANCE().getRootRef().child("Users").child(Objects.requireNonNull(snapshot.getKey())).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User_Data user_data = snapshot.getValue(User_Data.class);
                        groupMemberList.add(user_data);
                        memcount.setText(String.valueOf(groupMemberList.size()));
                        memberAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void memberClick(int position, String memberId) {
        Intent intent = new Intent(getActivity(), other_profile_activity.class);
        intent.putExtra("position",position);
        intent.putExtra("memberId",memberId);
        startActivity(intent);
    }
}
