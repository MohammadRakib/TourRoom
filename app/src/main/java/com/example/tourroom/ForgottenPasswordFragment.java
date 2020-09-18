package com.example.tourroom;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


public class ForgottenPasswordFragment extends Fragment {
    private TextInputEditText emailEditTextForgottenPassword;
    private Button sendEmailButtonForgottenPassword;
    private FirebaseAuth mAuth;
    public ForgottenPasswordFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_forgotten_password, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        emailEditTextForgottenPassword=view.findViewById(R.id.email_edittext_forgotten_password);
        sendEmailButtonForgottenPassword=view.findViewById(R.id.sendEmail_forgotten_password);
        sendEmailButtonForgottenPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail=emailEditTextForgottenPassword.getText().toString();
                if(TextUtils.isEmpty(userEmail))
                {
                    Toast.makeText(getActivity(), "Please enter your valid email first....",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                emailEditTextForgottenPassword.setText("");
                                Toast.makeText(getActivity(), "Please check your Email inbox,if you want to reset your password",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                String message=task.getException().getMessage();
                                Toast.makeText(getActivity(), "Error Occured"+message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}