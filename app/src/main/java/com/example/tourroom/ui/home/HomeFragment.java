package com.example.tourroom.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

    public BottomNavigationView bottomNavigationView;
    public Toolbar main_toolbar;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);


        //toolbar
        main_toolbar = root.findViewById(R.id.main_toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(main_toolbar);
        //bottom navigation
        bottomNavigationView = root.findViewById(R.id.bottom_home_nav);
        final NavController navController = Navigation.findNavController(root.findViewById(R.id.bottom_nav_host_fragment));
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        Menu bottom_menu = bottomNavigationView.getMenu();
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(bottom_menu.getItem(3).getItemId());
        badgeDrawable.setVisible(true);

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


    }

}
