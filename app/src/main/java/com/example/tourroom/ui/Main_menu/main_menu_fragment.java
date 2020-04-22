package com.example.tourroom.ui.Main_menu;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tourroom.R;
import com.google.android.material.card.MaterialCardView;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class main_menu_fragment extends Fragment {

    public main_menu_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_menu_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).isShowing()){
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
        }

        final NavController navController = Navigation.findNavController(requireActivity(), R.id.after_login_host_fragment);

        MaterialCardView profile = view.findViewById(R.id.option_profile);
        MaterialCardView security = view.findViewById(R.id.option_security);
        MaterialCardView about = view.findViewById(R.id.option_about);
        MaterialCardView logout = view.findViewById(R.id.option_logout);

        //onclick methods

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_Profile);
            }
        });

        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.security_fragment);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.about_fragment);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Log out", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
