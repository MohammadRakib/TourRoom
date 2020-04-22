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

public class general_notification_tab extends Fragment {

    RecyclerView recyclerView;
    general_notification_adapter general_notification_adapter;

    public static general_notification_tab newInstance() {
        return new general_notification_tab();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        General_Notification_Tab_ViewModel mViewModel = new ViewModelProvider(this).get(General_Notification_Tab_ViewModel.class);
        return inflater.inflate(R.layout.general_notification_tab_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.general_notification_recyleview_id);
        general_notification_adapter = new general_notification_adapter();
        recyclerView.setAdapter(general_notification_adapter);
    }
}
