package com.example.tourroom.ui.group;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourroom.Data.User_Data;
import com.example.tourroom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;

public class invite_other_activity extends AppCompatActivity{

    RecyclerView inviteOtherRecycleView;
    inviteOthersAdapter inviteOthersAdapter;
    private String currentUserID;
    List<User_Data> inviteOtherList;
    private int position;
    private String groupId;
    static public List<String> CheckList;
    TextView selectAll;
    Button SendInvite;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_other_activity);

        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
        inviteOtherList = new ArrayList<>();
        CheckList = new ArrayList<>();

        inviteOtherRecycleView = findViewById(R.id.inviteOtherRecycleView);
        selectAll = findViewById(R.id.selectAll_InviteOthers);
        SendInvite = findViewById(R.id.Invitebutton);
        inviteOthersAdapter = new inviteOthersAdapter(inviteOtherList, false,this);
        inviteOtherRecycleView.setAdapter(inviteOthersAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //for shared activity animation
            position = extras.getInt("position");
            groupId = getYourGroupListInstance().getYourGroupList().get(position).getGroupId();
            loadFollower();

        }

        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviteOthersAdapter = new inviteOthersAdapter(inviteOtherList, true,invite_other_activity.this);
                inviteOtherRecycleView.setAdapter(inviteOthersAdapter);
            }
        });

        SendInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!CheckList.isEmpty()){
                    for (int i = 0; i < CheckList.size(); i++){
                        getINSTANCE().getRootRef().child("Invitation").child(CheckList.get(i)).child(groupId).setValue("True")
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                    Toast.makeText(invite_other_activity.this, "Invitation Send", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });


    }

    private void loadFollower() {

        getINSTANCE().getRootRef().child("UserFollower").child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (final DataSnapshot data : snapshot.getChildren()){

                    getINSTANCE().getRootRef().child("GROUP").child(groupId).child("members").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.hasChild(Objects.requireNonNull(data.getKey()))){
                                getINSTANCE().getRootRef().child("Users").child(Objects.requireNonNull(data.getKey())).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        User_Data user_data = snapshot.getValue(User_Data.class);
                                        inviteOtherList.add(user_data);
                                        inviteOthersAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                loadFollowing();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadFollowing() {

         getINSTANCE().getRootRef().child("UserFollowing").child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (final DataSnapshot data : snapshot.getChildren()){

                    getINSTANCE().getRootRef().child("GROUP").child(groupId).child("members").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.hasChild(Objects.requireNonNull(data.getKey()))){
                                getINSTANCE().getRootRef().child("Users").child(Objects.requireNonNull(data.getKey())).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        User_Data user_data = snapshot.getValue(User_Data.class);
                                        inviteOtherList.add(user_data);
                                        inviteOthersAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
