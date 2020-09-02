package com.example.tourroom.ui.login;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tourroom.After_login_Activity;
import com.example.tourroom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class login_fragment extends Fragment {


    public static login_fragment newInstance() {
        return new login_fragment();
    }

    NavController navController;
    TextInputEditText email, password;
    private FirebaseUser currentUser;
    private ProgressDialog loadingBar;

    @Override
    public void onStart() {
        super.onStart();
        if(currentUser != null){
            Intent intent = new Intent(getActivity(), After_login_Activity.class);
            startActivity(intent);
            requireActivity().finishAffinity();
        }
    }

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
        email = view.findViewById(R.id.email_edittext);
        password = view.findViewById(R.id.password_edittext);
        loadingBar = new ProgressDialog(requireActivity());

        currentUser = getINSTANCE().getMAuth().getCurrentUser();
        navController = Navigation.findNavController(view);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                //login the user
                login();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_login_fragment_to_registration_fragment);
            }
        });
    }

    //login user
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void login() {

        String user_email = Objects.requireNonNull(email.getText()).toString();
        String user_password = Objects.requireNonNull(password.getText()).toString();

        if (TextUtils.isEmpty(user_email))
        {
            email.setError("Please enter your email Id");
        }
        else if (TextUtils.isEmpty(user_password))
        {
            password.setError("Please enter your password");
        }
        else
        {
            loadingBar.setTitle("Login");
            loadingBar.setMessage("Please wait, Login to the account...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            getINSTANCE().getMAuth().signInWithEmailAndPassword(user_email,user_password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            loadingBar.dismiss();
                            Intent intent = new Intent(getActivity(), After_login_Activity.class);
                            startActivity(intent);
                            requireActivity().finishAffinity();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingBar.dismiss();
                            password.setError(e.toString());
                        }
                    });
        }
    }
}
