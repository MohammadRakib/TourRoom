package com.example.tourroom.ui.feed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourroom.Data.commentData;
import com.example.tourroom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;
import static java.util.Objects.requireNonNull;

public class commentActivity extends AppCompatActivity {

    RecyclerView commentRecycleView;
    commentAdapter commentAdapter;
    List<commentData> commentDataList;
    EditText commentEditText;
    AppCompatImageButton commentSend;
    private String postId, userId;
    private String currentUserID;
    private String currentUserName;
    Query commentLoadQuery;
    ChildEventListener commentChildListener;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        commentDataList = new ArrayList<>();
        commentRecycleView = findViewById(R.id.commentRecycleView);
        commentEditText =  findViewById(R.id.commentEditText);
        commentSend =  findViewById(R.id.send_comment);
        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //for shared activity animation
            postId = extras.getString("postId");
            userId = extras.getString("userId");

        }

        getCurrentUserData();

        commentAdapter = new commentAdapter(commentDataList,this);
        commentRecycleView.setAdapter(commentAdapter);

        commentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentComment();
            }
        });

        loadComment();
    }

    private void sentComment() {

        String commentText = commentEditText.getText().toString();
        if(!commentText.isEmpty() && postId!=null){
            String commentKey = getINSTANCE().getRootRef().child("postComment").child(postId).push().getKey();
            final String notificationKey = getINSTANCE().getRootRef().child("notification").child(userId).push().getKey();
            commentData commentData = new commentData(commentKey,currentUserName,commentText);
            assert commentKey != null;
            getINSTANCE().getRootRef().child("postComment").child(postId).child(commentKey).setValue(commentData).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    commentEditText.setText("");
                    getINSTANCE().getRootRef().child("post").child(userId).child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String commentNumber = requireNonNull(snapshot.child("commentNumber").getValue()).toString();
                            int temp = Integer.parseInt(commentNumber) + 1;
                            String notificationString = currentUserName+" has commented in your post";

                            final Map<String, Object> update = new HashMap<>(); //this hashmap is used to write different data in different path in the database at once or atomically
                            update.put("post/"+userId+"/"+postId+"/commentNumber",String.valueOf(temp));
                            update.put("notification/"+userId+"/"+notificationKey,notificationString);
                            getINSTANCE().getRootRef().updateChildren(update);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(commentActivity.this, "Could not send the comment, try again", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void loadComment() {
        commentLoadQuery = getINSTANCE().getRootRef().child("postComment").child(postId);
        commentChildListener = commentLoadQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                commentData commentData = snapshot.getValue(commentData.class);
                commentDataList.add(commentData);
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCurrentUserData() {

        getINSTANCE().getRootRef().child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    currentUserName = requireNonNull(dataSnapshot.child("name").getValue()).toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        commentLoadQuery.removeEventListener(commentChildListener);
    }
}