package com.example.tourroom.ui.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.example.tourroom.R;

import static com.example.tourroom.ui.group.group_fragment.btn;

public class group_host_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_navhost);
        NavController navController = Navigation.findNavController(this,R.id.group_nav_host_id);
        if(btn==1){
            navController.popBackStack();
            navController.navigate(R.id.testfragment12);
        }else if (btn==2){
            navController.popBackStack();
            navController.navigate(R.id.testfragment23);
        }
    }
}
