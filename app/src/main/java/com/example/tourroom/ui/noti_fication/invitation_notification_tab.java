package com.example.tourroom.ui.noti_fication;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.tourroom.R;

public class invitation_notification_tab extends Fragment {

    RecyclerView recyclerView;
    invitation_adapter invitation_adapter;


    public static invitation_notification_tab newInstance() {
        return new invitation_notification_tab();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Invitation_Notification_Tab_ViewModel mViewModel = new ViewModelProvider(this).get(Invitation_Notification_Tab_ViewModel.class);
        return inflater.inflate(R.layout.invitation_notification_tab_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.invitation_recycleview_id);
        invitation_adapter = new invitation_adapter();
        recyclerView.setAdapter(invitation_adapter);

    }
}
