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
import com.example.tourroom.Data.group_data;
import com.example.tourroom.Data.yourGroupData;
import com.example.tourroom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tourroom.After_login_Activity.yourGroupIntoId;
import static com.example.tourroom.After_login_Activity.yourGroupIntoPosition;
import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;


public class group_host_activity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public Toolbar toolbar;
    NavController navController;
    TextView state,group_name;
    CircleImageView group_image;
    private int position = -1;
    private String currentUserID;
    private String groupId;
    String selectadmin;
    boolean memberListNumberNotZero = false;

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

        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();

        // problem with activity when go to landscape mode fix
        activity_open_fix();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //for shared activity animation
            position = extras.getInt("position");
            groupId = getYourGroupListInstance().getYourGroupList().get(position).getGroupId();

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(position != -1){
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
                intent.putExtra("position",position);
                startActivity(intent);
                return true;

            case R.id.group_info:
                Intent intent2 = new Intent(this,edit_group_info_activity.class);
                intent2.putExtra("position",position);
                startActivity(intent2);
                return true;

            case R.id.leave:
                leaveGroup();
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



    private void leaveGroup() {

        getINSTANCE().getRootRef().child("GROUP").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                group_data group_data = snapshot.getValue(group_data.class);

                assert group_data != null;
                if(currentUserID.equals(group_data.getGroupAdmin())){
                    selectAdmin();

                }else {
                    final Map<String, Object> update = new HashMap<>(); //this hashmap is used to write different data in different path in the database at once or atomically
                    update.put("GROUP/"+groupId+"/members/"+currentUserID,null);
                    update.put("Users/"+currentUserID+"/joinedGroups/"+groupId,null);
                    getINSTANCE().getRootRef().updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(group_host_activity.this, "Leaved the group", Toast.LENGTH_SHORT).show();
                            getYourGroupListInstance().getYourGroupList().remove(position);
                            yourGroupIntoPosition = -1;
                            yourGroupIntoId = null;
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(group_host_activity.this, "could not leaved the group, try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void selectAdmin() {

        getINSTANCE().getRootRef().child("GROUP").child(groupId).child("members").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                memberListNumberNotZero = false;
                for (DataSnapshot data : snapshot.getChildren()){
                    if(!Objects.equals(data.getKey(), currentUserID)){
                        selectadmin = data.getKey();
                        final Map<String, Object> update = new HashMap<>(); //this hashmap is used to write different data in different path in the database at once or atomically
                        update.put("GROUP/"+groupId+"/groupAdmin",selectadmin);
                        update.put("GROUP/"+groupId+"/members/"+currentUserID,null);
                        update.put("Users/"+currentUserID+"/joinedGroups/"+groupId,null);
                        memberListNumberNotZero = true;

                        getINSTANCE().getRootRef().updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(group_host_activity.this, "Leaved the group", Toast.LENGTH_SHORT).show();
                                getYourGroupListInstance().getYourGroupList().remove(position);
                                yourGroupIntoPosition = -1;
                                yourGroupIntoId = null;
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(group_host_activity.this, "could not leaved the group, try again", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    }
                }
                if(!memberListNumberNotZero){
                    final Map<String, Object> update = new HashMap<>(); //this hashmap is used to write different data in different path in the database at once or atomically
                    update.put("GROUP/"+groupId,null);
                    update.put("Users/"+currentUserID+"/joinedGroups/"+groupId,null);
                    update.put("groupMessage/"+groupId,null);
                    getINSTANCE().getRootRef().updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(group_host_activity.this, "Leaved the group", Toast.LENGTH_SHORT).show();
                            getYourGroupListInstance().getYourGroupList().remove(position);
                            yourGroupIntoPosition = -1;
                            yourGroupIntoId = null;
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(group_host_activity.this, "could not leaved the group, try again", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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