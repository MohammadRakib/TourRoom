package com.example.tourroom.ui.feed;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tourroom.Data.postdata;
import com.example.tourroom.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class feed_fragment extends Fragment implements feedInterface{
    RecyclerView feed_recycler_view;
    Feed_Recycler_Adapter feed_recycler_adapter;
    private String currentUserID;
    List<postdata> userPostList;
    boolean noFollower = true;
    boolean noPost = true;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FeedFragmentViewModel mViewModel = new ViewModelProvider(this).get(FeedFragmentViewModel.class);
        return inflater.inflate(R.layout.feed_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).isShowing()) {
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
           }

        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
        userPostList = new ArrayList<>();
        noFollower = true;
        noPost = true;

            feed_recycler_view=view.findViewById(R.id.feed_recyclerview);
            feed_recycler_adapter=new Feed_Recycler_Adapter(userPostList,requireActivity(),this);

            feed_recycler_view.setLayoutManager(new LinearLayoutManager(requireActivity()));

            feed_recycler_view.setAdapter(feed_recycler_adapter);

            DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(requireActivity(),DividerItemDecoration.VERTICAL);
            feed_recycler_view.addItemDecoration(dividerItemDecoration);

            loadPost();


       /* Button button=view.findViewById(R.id.other_profile_from_feed);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), other_profile_activity.class);
                startActivity(intent);
            }
        });*/
    }

    public void loadPost(){

        getINSTANCE().getRootRef().child("UserFollowing").child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    String FollowingUserId = data.getKey();
                    noFollower = false;
                    assert FollowingUserId != null;
                    getINSTANCE().getRootRef().child("post").child(FollowingUserId).limitToLast(3).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()){
                                noPost = false;
                                postdata postdata = data.getValue(postdata.class);
                                userPostList.add(postdata);
                                feed_recycler_adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                //user is not following anyone
                if(noFollower || noPost){
                   
                    getINSTANCE().getRootRef().child("Users").limitToFirst(3).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data : snapshot.getChildren()){
                                String UserId = data.getKey();
                                assert UserId != null;
                                getINSTANCE().getRootRef().child("post").child(UserId).limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot data : snapshot.getChildren()){
                                            postdata postdata = data.getValue(postdata.class);
                                            userPostList.add(postdata);
                                            feed_recycler_adapter.notifyDataSetChanged();
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
