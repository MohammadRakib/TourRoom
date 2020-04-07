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

import java.util.Objects;


public class notification_fragment extends Fragment {

    private NotificationFragmentViewModel mViewModel;
    private TabLayoutMediator tabLayoutMediator;
    DrawerLayout drawer;

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
        drawer = Objects.requireNonNull(getActivity()).findViewById(R.id.after_login_layout_id);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.main_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getMenu().findItem(R.id.search).setVisible(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getMenu().findItem(R.id.notification).setVisible(false);
        }
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
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        tabLayoutMediator.detach();
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.main_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getMenu().findItem(R.id.search).setVisible(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getMenu().findItem(R.id.notification).setVisible(true);
        }

    }
}
