package com.example.tourroom.ui.member;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;

public class Member_Requests_fragment extends Fragment {

    private MemberRequestsFragmentViewModel mViewModel;
    private View view;
    @Nullable
    private Bundle savedInstanceState;
    RecyclerView memberrequestRecycleView;
   memeberRequestAdapter memberrequestAdapter;
    List<User_Data> memberRequestList;
    private int position;
    private String groupId;
    static public List<String> CheckListForMemberRequest;
    TextView selectAll;
    Button acceptRequest, rejectRequest;
    private String currentUserID;
    private group_host_activity group_host_activity;
    private NavController navController;

    public static Member_Requests_fragment newInstance() {
        return new Member_Requests_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.member__requests_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
        memberRequestList = new ArrayList<>();
        CheckListForMemberRequest = new ArrayList<>();
        //getting data from activity
        group_host_activity = (group_host_activity) requireActivity();
        position = group_host_activity.getPosition();
        groupId = getYourGroupListInstance().getYourGroupList().get(position).getGroupId();
        navController = Navigation.findNavController(requireActivity(),R.id.group_nav_host_id);

        selectAll = view.findViewById(R.id.selectAll_memberRequest);
        acceptRequest = view.findViewById(R.id.acceptButton);
        rejectRequest = view.findViewById(R.id.rejectButton);
        memberrequestRecycleView = view.findViewById(R.id.memberrequest_recycleview);
        memberrequestAdapter = new memeberRequestAdapter(memberRequestList,false, requireActivity());
        memberrequestRecycleView.setAdapter(memberrequestAdapter);

        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberrequestAdapter = new memeberRequestAdapter(memberRequestList,true, requireActivity());
                memberrequestRecycleView.setAdapter(memberrequestAdapter);
            }
        });

        acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckListForMemberRequest.isEmpty()){
                    for (int i = 0; i < CheckListForMemberRequest.size(); i++){
                        final Map<String, Object> update = new HashMap<>(); //this hashmap is used to write different data in different path in the database at once or atomically
                        update.put("GROUP/"+groupId+"/members/"+CheckListForMemberRequest.get(i),true);
                        update.put("Users/"+CheckListForMemberRequest.get(i)+"/joinedGroups/"+groupId+"/msgCountUser",getYourGroupListInstance().getYourGroupList().get(position).getMsgCount());
                        update.put("groupMemberRequest/"+groupId+"/"+CheckListForMemberRequest.get(i),null);

                        getINSTANCE().getRootRef().updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(requireActivity(), "Request Accepted Successfully", Toast.LENGTH_SHORT).show();
                                navController.popBackStack();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireActivity(), "Could not accept the request, try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });

        rejectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!CheckListForMemberRequest.isEmpty()) {
                    for (int i = 0; i < CheckListForMemberRequest.size(); i++) {
                         getINSTANCE().getRootRef().child("groupMemberRequest").child(groupId).child(CheckListForMemberRequest.get(i)).setValue(null)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(requireActivity(), "Request rejected", Toast.LENGTH_SHORT).show();
                                        navController.popBackStack();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireActivity(), "Could not reject request", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });

      loadMemberRequest();

    }

    private void loadMemberRequest() {

        getINSTANCE().getRootRef().child("groupMemberRequest").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (final DataSnapshot data : snapshot.getChildren()){
                    getINSTANCE().getRootRef().child("Users").child(Objects.requireNonNull(data.getKey())).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User_Data user_data = snapshot.getValue(User_Data.class);
                            memberRequestList.add(user_data);
                            memberrequestAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
