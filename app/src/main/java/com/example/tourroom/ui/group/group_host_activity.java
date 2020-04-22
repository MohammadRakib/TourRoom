package com.example.tourroom.ui.group;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourroom.R;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class group_host_activity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public Toolbar toolbar;
    NavController navController;
    TextView state,group_name;
    CircleImageView group_image;
    ConstraintLayout chat_box;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_navhost);
        navController = Navigation.findNavController(this,R.id.group_nav_host_id);
        toolbar = findViewById(R.id.groupToolbar);
        setSupportActionBar(toolbar);

        //share animation flash fix
        share_animation_flash_fix();

        //layout component
        state = findViewById(R.id.group_state);
        group_image = findViewById(R.id.group_image);
        group_name = findViewById(R.id.group_title);
        chat_box = findViewById(R.id.chat_box);

        // problem with activity when go to landscape mode fix
        activity_open_fix();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //for shared activity animation
            int position = extras.getInt("position");
            group_image.setTransitionName("gimg"+position);
            group_name.setTransitionName("gnm"+position);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void share_animation_flash_fix() {
        Fade fade = new Fade();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fade.excludeTarget(findViewById(R.id.group_appbar_layout_id), true);
            fade.excludeTarget(chat_box,true);
            fade.excludeTarget(findViewById(R.id.group_nav_host_id),true);
            fade.excludeTarget(R.layout.group_navhost,true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }
    }


    // problem with activity when go to landscape mode is fixed here
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void activity_open_fix() {

        if(Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.chat_fragment){ //if activity don't open with chat fragment

            chat_box.setVisibility(View.INVISIBLE);//chat box set invisible
            if(Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.members_fragment){ //if activity open with member fragment

                state.setText(R.string.members);

            }else if(Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.member_Requests_fragment){ //if activity open with member request fragment

                state.setText(R.string.member_requests);

            }else if(Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.poll_fragment){ //if activity open with poll fragment

                state.setText(R.string.poll);

            }else if(Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.event_fragment){ //if activity open with event fragment

                state.setText(R.string.events);

            }else if(Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.announcement_fragment){ //if activity open with announcement fragment

                state.setText(R.string.announcements);

            }
        }else {

            state.setText(R.string.chat);

        }
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
                if(!chat_box.isShown()){
                    chat_box.setVisibility(View.VISIBLE);
                }
                return true;

            case R.id.member:
                navController.navigate(R.id.members_fragment);
                state.setText(R.string.members);
                if(chat_box.isShown()){
                    chat_box.setVisibility(View.INVISIBLE);
                }
                return true;

            case R.id.poll:
                navController.navigate(R.id.poll_fragment);
                state.setText(R.string.poll);
                if(chat_box.isShown()){
                    chat_box.setVisibility(View.INVISIBLE);
                }
                return true;

            case R.id.event:
                navController.navigate(R.id.event_fragment);
                state.setText(R.string.events);
                if(chat_box.isShown()){
                    chat_box.setVisibility(View.INVISIBLE);
                }
                return true;

            case R.id.announcement:
                navController.navigate(R.id.announcement_fragment);
                state.setText(R.string.announcements);
                if(chat_box.isShown()){
                    chat_box.setVisibility(View.INVISIBLE);
                }
                return true;

            case R.id.member_request:
                navController.navigate(R.id.member_Requests_fragment);
                state.setText(R.string.member_requests);
                if(chat_box.isShown()){
                    chat_box.setVisibility(View.INVISIBLE);
                }
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        state.setText(R.string.chat);
        if(!chat_box.isShown()){
            chat_box.setVisibility(View.VISIBLE);
        }
        navController.popBackStack(R.id.chat_fragment,false);
    }
}
