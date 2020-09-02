package com.example.tourroom.ui.Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.tourroom.After_login_Activity;
import com.example.tourroom.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;

import java.util.HashMap;
import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class Registration_fragment extends Fragment {

    public static Registration_fragment newInstance() {
        return new Registration_fragment();
    }

    private RegistrationFragmentViewModel mViewModel;

    TextInputEditText user_name, user_email, user_password, confirm_password;
    Button register;
    TextView alreadyHaveAccount;
    NavController navController;
    private ProgressDialog loadingBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(RegistrationFragmentViewModel.class);
        return inflater.inflate(R.layout.registration_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        user_name = view.findViewById(R.id.username_edittext);
        user_email = view.findViewById(R.id.email_edittext);
        user_password = view.findViewById(R.id.password_edittext);
        confirm_password = view.findViewById(R.id.retype_password_edittext);
        register = view.findViewById(R.id.register_button);
        alreadyHaveAccount = view.findViewById(R.id.already_account_textview);
        loadingBar = new ProgressDialog(requireActivity());

        navController = Navigation.findNavController(view);

        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                //register
                register();
            }
        });

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registration_fragment_to_login_fragment);
            }
        });

    }

    //register an account
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void register() {

        final String email = Objects.requireNonNull(user_email.getText()).toString();
        String password = Objects.requireNonNull(user_password.getText()).toString();
        String confirm_pass = Objects.requireNonNull(confirm_password.getText()).toString();
        final String Name = Objects.requireNonNull(user_name.getText()).toString();

        if (TextUtils.isEmpty(Name)){
            user_name.setError("Please write your User Name");
         }
        else if(TextUtils.isEmpty(email)){
            user_email.setError("Please write your Email Id");
        }
        else if (TextUtils.isEmpty(password)){
            user_password.setError("Please write a password");
        }
        else if (TextUtils.isEmpty(confirm_pass)){
            confirm_password.setError("Please re-write the password");
        }
        else if(!password.equals(confirm_pass)){
             confirm_password.setError("Password is not matched");
        }else {
            loadingBar.setTitle("Register");
            loadingBar.setMessage("Please wait, your Account is creating...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            getINSTANCE().getMAuth().createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            if(getINSTANCE().getMAuth().getCurrentUser()!=null){
                                String currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
                                HashMap<String, Object> UserMap = new HashMap<>();
                                UserMap.put("uid",currentUserID);
                                UserMap.put("UEmail",email);
                                UserMap.put("name",Name);

                                getINSTANCE().getRootRef().child("Users").child(currentUserID).setValue(UserMap);

                                loadingBar.dismiss();
                                Toast.makeText(requireActivity(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), After_login_Activity.class);
                                startActivity(intent);
                                requireActivity().finishAffinity();
                            }else {
                                loadingBar.dismiss();
                                Toast.makeText(requireActivity(), "Network Connection error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadingBar.dismiss();
                            user_password.setError(e.toString());
                        }
                    });
        }
    }
}
