package com.example.tourroom.ui.noti_fication;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tourroom.Data.User_Data;
import com.example.tourroom.Data.group_data;
import com.example.tourroom.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class invitation_notification_tab extends Fragment {

    RecyclerView recyclerView;
    invitation_adapter invitation_adapter;
    List<group_data> invitationList;
    private String currentUserID;
    Query invitationLoadQuery;
    ChildEventListener invitationLoadListener;


    public static invitation_notification_tab newInstance() {
        return new invitation_notification_tab();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Invitation_Notification_Tab_ViewModel mViewModel = new ViewModelProvider(this).get(Invitation_Notification_Tab_ViewModel.class);
        return inflater.inflate(R.layout.invitation_notification_tab_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.invitation_recycleview_id);
        invitationList = new ArrayList<>();
        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();

        invitation_adapter = new invitation_adapter(invitationList,requireActivity());
        recyclerView.setAdapter(invitation_adapter);

        loadInvitation();

    }

    private void loadInvitation() {

        invitationLoadQuery =  getINSTANCE().getRootRef().child("Invitation").child(currentUserID);
        invitationLoadListener = invitationLoadQuery.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                getINSTANCE().getRootRef().child("GROUP").child(Objects.requireNonNull(snapshot.getKey())).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        group_data group_data = snapshot.getValue(group_data.class);
                        assert group_data != null;
                        invitationList.add(group_data);
                        invitation_adapter.notifyDataSetChanged();
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

                for(int i =0; i<invitationList.size(); i++){
                    if(invitationList.get(i).getGroupId().equals(snapshot.getKey())){
                        invitationList.remove(i);
                        invitation_adapter.notifyDataSetChanged();
                        break;
                    }
                }
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
    public void onDestroy() {
        super.onDestroy();
        invitationLoadQuery.removeEventListener(invitationLoadListener);
    }
}
