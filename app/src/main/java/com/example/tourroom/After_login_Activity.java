package com.example.tourroom;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.yourGroupData;
import com.example.tourroom.ui.group.group_vertical_parent_recycle_view_adapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;
import static com.example.tourroom.ui.group.group_fragment.group_vertical_parent_recycle_view_adapterVariable;

public class After_login_Activity extends AppCompatActivity {

    public static String UserName, UserEmail, UserImage;
    private String currentUserID;
    private ProgressDialog loadingBar;
    //listener and query for newMessageTracker
    Query newMessageQuery;
    ChildEventListener newMessageListener;

    boolean breaks = false; //for breaking the loop in the listener;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_login);

        loadingBar = new ProgressDialog(this);
        loadingBar.setTitle("Loading");
        loadingBar.setMessage("Please wait, Loading your data");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        //getting current login user
        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
        loadUserProfile();
        loadGroup();

        //track new message dynamically
        newMessageTracker();


    }



    public void loadUserProfile(){

        getINSTANCE().getRootRef().child("Users").child(currentUserID)
                .addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists() && dataSnapshot.hasChild("image")){
                            UserName = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                            UserEmail = Objects.requireNonNull(dataSnapshot.child("UEmail").getValue()).toString();
                            UserImage = Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();
                            loadingBar.dismiss();

                        }else if(dataSnapshot.exists()) {
                            UserName = Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                            UserEmail = Objects.requireNonNull(dataSnapshot.child("UEmail").getValue()).toString();
                           // loadingBar.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                          // loadingBar.dismiss();
                    }
                });
    }

    private void loadGroup() {

        getYourGroupListInstance().getYourGroupList().clear();//clearing the list if fragment got destroyed and recreated

        //getting last messages and message count(specific to current user not the group itself) for joined groups
        getINSTANCE().getRootRef().child("Users").child(currentUserID).child("joinedGroups").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()){

                    yourGroupData groupData = data.getValue(yourGroupData.class); //getting data

                    if(groupData !=null){

                        groupData.setGroupId(data.getKey());
                        getYourGroupListInstance().getYourGroupList().add(groupData);
                    }
                }


                //getting joined group information
                for (int i=0; i<getYourGroupListInstance().getYourGroupList().size(); i++){

                    final int finalI = i; //temp variable
                    getINSTANCE().getRootRef().child("GROUP").child(getYourGroupListInstance().getYourGroupList().get(i).getGroupId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            getYourGroupListInstance().getYourGroupList().get(finalI).setMsgCount(Objects.requireNonNull(dataSnapshot.child("msgCount").getValue()).toString());
                            getYourGroupListInstance().getYourGroupList().get(finalI).setGroupName(Objects.requireNonNull(dataSnapshot.child("groupName").getValue()).toString());

                            if(dataSnapshot.hasChild("groupImage")){ //if has group image
                                getYourGroupListInstance().getYourGroupList().get(finalI).setGroupImage(Objects.requireNonNull(dataSnapshot.child("groupImage").getValue()).toString());
                            }
                            loadingBar.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            loadingBar.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingBar.dismiss();
            }
        });

    }

    private void newMessageTracker(){

        newMessageQuery = getINSTANCE().getRootRef().child("Users").child(currentUserID).child("joinedGroups");
        newMessageListener = newMessageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for (int i=0; i<getYourGroupListInstance().getYourGroupList().size(); i++){
                    if(getYourGroupListInstance().getYourGroupList().get(i).getGroupId().equals(dataSnapshot.getKey())){

                        //updating last message
                        getYourGroupListInstance().getYourGroupList().get(i).setLastmsgUserName(Objects.requireNonNull(dataSnapshot.child("lastmsgUserName").getValue()).toString());
                        getYourGroupListInstance().getYourGroupList().get(i).setLastMessage(Objects.requireNonNull(dataSnapshot.child("lastMessage").getValue()).toString());
                        getYourGroupListInstance().getYourGroupList().get(i).setLastmsgTime(Objects.requireNonNull(dataSnapshot.child("lastmsgTime").getValue()).toString());

                        // updating user message count
                        final int finalI = i;

                        getINSTANCE().getRootRef().child("GROUP").child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                getYourGroupListInstance().getYourGroupList().get(finalI).setMsgCount(Objects.requireNonNull(dataSnapshot.child("msgCount").getValue()).toString());
                                yourGroupData temp =  getYourGroupListInstance().getYourGroupList().remove(finalI);
                                getYourGroupListInstance().getYourGroupList().add(0,temp);

                                if(group_vertical_parent_recycle_view_adapterVariable != null){
                                    group_vertical_parent_recycle_view_adapterVariable.notifyDataSetChanged();
                                }

                                breaks = true;
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    if(breaks){
                        breaks = false;
                        break;
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newMessageQuery.removeEventListener(newMessageListener);
    }
}
