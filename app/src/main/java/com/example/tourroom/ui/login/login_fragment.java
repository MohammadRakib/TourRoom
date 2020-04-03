package com.example.tourroom.ui.login;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tourroom.After_login_Activity;
import com.example.tourroom.R;

import java.util.Objects;

public class login_fragment extends Fragment {


    public static login_fragment newInstance() {
        return new login_fragment();
    }

    NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.login_fragment, container, false);
        LoginFragmentViewModel loginFragmentViewModel = new ViewModelProvider(this).get(LoginFragmentViewModel.class);


        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = view.findViewById(R.id.login);
        Button button1 = view.findViewById(R.id.register);
        navController = Navigation.findNavController(view);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), After_login_Activity.class);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).finishAffinity();

            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_login_fragment_to_registration_fragment);
            }
        });
    }
}
