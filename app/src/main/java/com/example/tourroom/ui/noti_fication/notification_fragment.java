package com.example.tourroom.ui.noti_fication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tourroom.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class notification_fragment extends Fragment {

    private NotificationFragmentViewModel mViewModel;
    private TabLayoutMediator tabLayoutMediator;

    public static notification_fragment newInstance() {
        return new notification_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(NotificationFragmentViewModel.class);
        return inflater.inflate(R.layout.notification_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.notification_tab_layout);
        ViewPager2 viewPager2 = view.findViewById(R.id.notification_viewpager);
        notification_page_adapter pagerAdapter = new notification_page_adapter(this);
        viewPager2.setAdapter(pagerAdapter);
        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
               if(position == 0){
                   tab.setText("General");
               }else if(position == 1){
                   tab.setText("Invitation");
               }
            }
        });
        tabLayoutMediator.attach();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tabLayoutMediator.detach();
    }
}
