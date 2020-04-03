package com.example.tourroom.ui.group;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourroom.R;



public class group_host_activity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public Toolbar toolbar;
    NavController navController;
    TextView state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_navhost);
        navController = Navigation.findNavController(this,R.id.group_nav_host_id);
        toolbar = findViewById(R.id.groupToolbar);
        setSupportActionBar(toolbar);
        state = findViewById(R.id.group_state);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.invite_other:
                Intent intent = new Intent(this,invite_other_activity.class);
                startActivity(intent);
                return true;

            case R.id.edit_group_info:
                Intent intent2 = new Intent(this,edit_group_info_activity.class);
                startActivity(intent2);
                return true;

            case R.id.leave:
                Toast.makeText(this, "leave", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return false;
        }
    }

    @SuppressLint("RestrictedApi")
    public void  group_popup_menu(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.group_state_menu);
        @SuppressLint("RestrictedApi") MenuPopupHelper menuHelper = new MenuPopupHelper(this,(MenuBuilder) popup.getMenu(),v);
        menuHelper.setForceShowIcon(true);
        menuHelper.setGravity(Gravity.END);
        menuHelper.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){
            case R.id.chat:
                navController.navigate(R.id.chat_fragment);
                state.setText(R.string.chat);
                return true;

            case R.id.member:
                navController.navigate(R.id.members_fragment);
                state.setText(R.string.members);
                return true;

            case R.id.poll:
                navController.navigate(R.id.poll_fragment);
                state.setText(R.string.poll);
                return true;

            case R.id.event:
                navController.navigate(R.id.event_fragment);
                state.setText(R.string.events);
                return true;

            case R.id.announcement:
                navController.navigate(R.id.announcement_fragment);
                state.setText(R.string.announcements);
                return true;

            case R.id.member_request:
                navController.navigate(R.id.member_Requests_fragment);
                state.setText(R.string.member_requests);
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        state.setText(R.string.chat);
        navController.popBackStack(R.id.chat_fragment,false);

    }
}
