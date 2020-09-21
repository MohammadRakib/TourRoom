package com.example.tourroom.ui.profile;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tourroom.Data.User_Data;
import com.example.tourroom.Data.postdata;
import com.example.tourroom.R;
import com.example.tourroom.ui.feed.commentActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static java.util.Objects.requireNonNull;

public class other_profile_activity extends AppCompatActivity implements otherProfileInterface{
    RecyclerView otherprofile_recycler_view;
    Otherprofile_Recycler_Adapter otherprofile_recycler_adapter;
    int position;
    String memberId;
    private String UserName,UserImage,UserId;
    otherProfileInterface otherProfileInterface;
    boolean ifFollowing;
    private String currentUser;
    List<postdata> userPostList;
    private boolean postLoadComplete = false;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_profile_activity);

        currentUser = requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
        otherprofile_recycler_view=findViewById(R.id.otherprofile_recyclerview);
        userPostList = new ArrayList<>();
        postLoadComplete = false;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //for shared activity animation
            position = extras.getInt("position");
            memberId = extras.getString("memberId");
            otherProfileLoad(this);
        }
        otherProfileInterface = this;

    }


    private void otherProfileLoad(final Context context) {
        getINSTANCE().getRootRef().child("Users").child(memberId).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User_Data user_data = snapshot.getValue(User_Data.class);
                assert user_data != null;
                UserName = user_data.getName();
                UserImage = user_data.getImage();
                UserId = user_data.getUid();
                checkIfFollowing(context);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void checkIfFollowing(final Context context) {
        getINSTANCE().getRootRef().child("UserFollowing").child(currentUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ifFollowing = snapshot.hasChild(UserId);

                otherprofile_recycler_adapter=new Otherprofile_Recycler_Adapter(context,UserName,UserImage,UserId,ifFollowing,userPostList,otherProfileInterface);
                otherprofile_recycler_view.setLayoutManager(new LinearLayoutManager(context));

                otherprofile_recycler_view.setAdapter(otherprofile_recycler_adapter);

                DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(context,DividerItemDecoration.VERTICAL);
                otherprofile_recycler_view.addItemDecoration(dividerItemDecoration);
                if(!postLoadComplete){
                    loadPost();
                    postLoadComplete = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadPost() {
        getINSTANCE().getRootRef().child("post").child(memberId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()){
                    postdata postdata = data.getValue(postdata.class);
                    userPostList.add(postdata);
                    otherprofile_recycler_adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onclickComment(String postId, String userId) {
        Intent intent = new Intent(other_profile_activity.this, commentActivity.class);
        intent.putExtra("postId",postId);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }
}
