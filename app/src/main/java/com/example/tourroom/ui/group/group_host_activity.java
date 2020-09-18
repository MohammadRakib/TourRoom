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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.yourGroupData;
import com.example.tourroom.R;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;


public class group_host_activity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public Toolbar toolbar;
    NavController navController;
    TextView state,group_name;
    CircleImageView group_image;
    private int position;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_navhost);
        navController = Navigation.findNavController(this,R.id.group_nav_host_id);
        toolbar = findViewById(R.id.groupToolbar);
        setSupportActionBar(toolbar);


        //layout component
        state = findViewById(R.id.group_state);
        group_image = findViewById(R.id.group_image);
        group_name = findViewById(R.id.group_title);


        // problem with activity when go to landscape mode fix
        activity_open_fix();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //for shared activity animation
            position = extras.getInt("position");

            loadGroupData(position);
        }
    }



    // problem with activity when go to landscape mode is fixed here
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void activity_open_fix() {

        if(Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.chat_fragment){ //if activity don't open with chat fragment

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


    private void loadGroupData(int position) {

        yourGroupData group_data = getYourGroupListInstance().getYourGroupList().get(position);

        if(group_data != null && group_data.getGroupImage() == null){

            String groupName = group_data.getGroupName();
            group_name.setText(groupName);

        }else if (group_data != null && group_data.getGroupImage() != null){

            String groupName = group_data.getGroupName();
            String groupImageUri = group_data.getGroupImage();

            group_name.setText(groupName);

            Glide.with(group_host_activity.this)
                    .load(groupImageUri)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .dontAnimate()
                    .placeholder(R.drawable.dummyimage)
                    .into(group_image);

        }

    }


    public int getPosition() {
        return position;
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        state.setText(R.string.chat);

        navController.popBackStack(R.id.chat_fragment,false);
    }
}
