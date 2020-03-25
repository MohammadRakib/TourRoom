package com.example.tourroom.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.tourroom.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class HomeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        //bottom navigation
        BottomNavigationView bottomNavigationView = root.findViewById(R.id.bottom_home_nav);
        NavController navController = Navigation.findNavController(root.findViewById(R.id.bottom_nav_host_fragment));
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        Menu bottom_menu = bottomNavigationView.getMenu();
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(bottom_menu.getItem(4).getItemId());
        badgeDrawable.setVisible(true);


        //view model related
        homeViewModel.get_pending_notification().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

            }
        });

        return root;
    }

}
