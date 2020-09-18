package com.example.tourroom.ui.Main_menu;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.After_login_Activity;
import com.example.tourroom.MainActivity;
import com.example.tourroom.R;
import com.google.android.material.card.MaterialCardView;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tourroom.After_login_Activity.UserEmail;
import static com.example.tourroom.After_login_Activity.UserImage;
import static com.example.tourroom.After_login_Activity.UserName;
import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

/**
 * A simple {@link Fragment} subclass.
 */
public class main_menu_fragment extends Fragment {

    MaterialCardView profile, security, about, logout, addNewPost;
    NavController navController;
    TextView UserNameTextView, UserEmailTextView;
    CircleImageView profileImage;



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

        navController = Navigation.findNavController(requireActivity(), R.id.after_login_host_fragment);

        profile = view.findViewById(R.id.option_profile);
        security = view.findViewById(R.id.option_security);
        about = view.findViewById(R.id.option_about);
        logout = view.findViewById(R.id.option_logout);
        addNewPost = view.findViewById(R.id.addNewPostCard);
        UserNameTextView = view.findViewById(R.id.user_name_text_view);
        UserEmailTextView = view.findViewById(R.id.user_Id_text_view);
        profileImage = view.findViewById(R.id.profile_image);

        //setting up user name, user email and user profile image
        UserNameTextView.setText(UserName);
        UserEmailTextView.setText(UserEmail);


            Glide.with(requireActivity())
                    .load(UserImage)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .dontAnimate()
                    .placeholder(R.drawable.dummyavatar)
                    .into(profileImage);


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
                getINSTANCE().getMAuth().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finishAffinity();
            }
        });

        addNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.addNewPostFragment);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
