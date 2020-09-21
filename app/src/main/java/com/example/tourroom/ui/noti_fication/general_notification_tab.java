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
import com.example.tourroom.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class general_notification_tab extends Fragment {

    RecyclerView recyclerView;
    general_notification_adapter general_notification_adapter;
    private String currentUserID;
    Query notificationLoadQuery;
    ChildEventListener notificationChildListener;
    List<String> notificationList;


    public static general_notification_tab newInstance() {
        return new general_notification_tab();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        General_Notification_Tab_ViewModel mViewModel = new ViewModelProvider(this).get(General_Notification_Tab_ViewModel.class);
        return inflater.inflate(R.layout.general_notification_tab_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.general_notification_recyleview_id);
        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
        notificationList = new ArrayList<>();

        general_notification_adapter = new general_notification_adapter(notificationList,requireActivity());
        recyclerView.setAdapter(general_notification_adapter);

        loadNotification();
    }

    private void loadNotification() {
       notificationLoadQuery = getINSTANCE().getRootRef().child("notification").child(currentUserID);
       notificationChildListener = notificationLoadQuery.addChildEventListener(new ChildEventListener() {
           @RequiresApi(api = Build.VERSION_CODES.KITKAT)
           @Override
           public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               String notificationString = Objects.requireNonNull(snapshot.getValue()).toString();
               notificationList.add(notificationString);
               general_notification_adapter.notifyDataSetChanged();
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
    public void onDestroy() {
        super.onDestroy();
        notificationLoadQuery.removeEventListener(notificationChildListener);
    }
}
