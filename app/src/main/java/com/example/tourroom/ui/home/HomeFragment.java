package com.example.tourroom.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.example.tourroom.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class HomeFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        //bottom navigation
        bottomNavigationView = root.findViewById(R.id.bottom_home_nav);
        NavController navController = Navigation.findNavController(root.findViewById(R.id.bottom_nav_host_fragment));
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        //notification badger
        /*Menu bottom_menu = bottomNavigationView.getMenu();
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(bottom_menu.getItem(3).getItemId());
        badgeDrawable.setVisible(true);*/

        //view model related
        homeViewModel.get_pending_notification().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.main_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getMenu().findItem(R.id.search).setVisible(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getMenu().findItem(R.id.notification).setVisible(false);
        }

    }

}
