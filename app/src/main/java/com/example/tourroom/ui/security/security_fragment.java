package com.example.tourroom.ui.security;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tourroom.MainActivity;
import com.example.tourroom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class security_fragment extends Fragment {

    private Security_Fragment_View_Model security_view_model;

    TextInputEditText new_password, old_password, re_type_new_password;
    Button change_password;
    private String oldPassword,newPassword,reTypeNewPassword;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.security_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        security_view_model = new ViewModelProvider(this).get(Security_Fragment_View_Model.class);
        new_password = view.findViewById(R.id.new_pass_edit_text);
        old_password = view.findViewById(R.id.old_pass_edit_text);
        re_type_new_password = view.findViewById(R.id.re_type_new_pass_edit_text);
        change_password = view.findViewById(R.id.change_password_button);
        old_password.addTextChangedListener(textWatcher);
        new_password.addTextChangedListener(textWatcher);
        re_type_new_password.addTextChangedListener(textWatcher);
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword = old_password.getText().toString();
                newPassword =  new_password.getText().toString();
                reTypeNewPassword= re_type_new_password.getText().toString();
                final FirebaseUser user;
                final FirebaseAuth fAuth;
                fAuth=FirebaseAuth.getInstance();
                user =fAuth.getCurrentUser();
                final String email = user.getEmail();
                AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if(newPassword.equals(reTypeNewPassword))
                            {
                                user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "password not updated", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), "password updated successfully", Toast.LENGTH_SHORT).show();
                                            getINSTANCE().getMAuth().signOut();
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            startActivity(intent);
                                            requireActivity().finishAffinity();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                Toast.makeText(getContext(), "New password and confirm password don't match", Toast.LENGTH_SHORT).show();
                                new_password.setText("");
                                old_password.setText("");
                                re_type_new_password.setText("");
                            }

                        } else {
                            Toast.makeText(getActivity(), "Authentication failed", Toast.LENGTH_SHORT).show();
                            getINSTANCE().getMAuth().signOut();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            requireActivity().finishAffinity();
                        }
                    }
                });
            }
        });
    }
    //watch edit text if it is empty or not
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String old_pass = Objects.requireNonNull(old_password.getText()).toString().trim();
            String new_pass = Objects.requireNonNull(new_password.getText()).toString().trim();
            String re_type_new_pass = Objects.requireNonNull(re_type_new_password.getText()).toString().trim();
            change_password.setEnabled(!old_pass.isEmpty() && !new_pass.isEmpty() && !re_type_new_pass.isEmpty()); //setting the button enable if all edit text are not empty
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
