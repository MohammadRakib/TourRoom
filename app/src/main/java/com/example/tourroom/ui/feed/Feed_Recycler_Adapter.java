package com.example.tourroom.ui.feed;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.postdata;
import com.example.tourroom.R;
import com.example.tourroom.ui.profile.Profile_Recycler_Adapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class Feed_Recycler_Adapter extends RecyclerView.Adapter<Feed_Recycler_Adapter.FeedViewHolder> {

    List<postdata> userPostList;
    Context context;
    private String userName,userImage;

    public Feed_Recycler_Adapter(List<postdata> userPostList, Context context) {
        this.userPostList = userPostList;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.feed_row_items,parent,false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedViewHolder holder, int position) {
        final postdata postdata = userPostList.get(position);
        final Feed_Recycler_Adapter.FeedViewHolder profileLowerViewHolder = holder;

        //load user data
        getINSTANCE().getRootRef().child("Users").child(postdata.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName = Objects.requireNonNull(snapshot.child("name").getValue()).toString();
                userImage = Objects.requireNonNull(snapshot.child("image").getValue()).toString();

                profileLowerViewHolder.username_textv.setText(userName);
                Glide.with(context)
                        .load(userImage)
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .dontAnimate()
                        .placeholder(R.drawable.dummyavatar)
                        .into(profileLowerViewHolder.user_imagev);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profileLowerViewHolder.like_count_textv.setText(postdata.getLikeNumber());
        profileLowerViewHolder.comment_count_textv.setText(postdata.getCommentNumber());

        Glide.with(context)
                .load(postdata.getPostImage())
                .format(DecodeFormat.PREFER_ARGB_8888)
                .dontAnimate()
                .placeholder(R.drawable.dummyavatar)
                .into(profileLowerViewHolder.post_imagev);

        profileLowerViewHolder.like_imagev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int temp = Integer.parseInt(postdata.getLikeNumber()) + 1;

                getINSTANCE().getRootRef().child("post").child(postdata.getUserId()).child(postdata.getPostId()).child("likeNumber").setValue(String.valueOf(temp))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                profileLowerViewHolder.like_count_textv.setText(String.valueOf(temp));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Could not like the picture", Toast.LENGTH_SHORT).show();
                        profileLowerViewHolder.like_count_textv.setText(postdata.getLikeNumber());
                    }
                });

            }
        });

        profileLowerViewHolder.comment_imagev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        profileLowerViewHolder.share_imagev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return userPostList.size();
    }

    static class FeedViewHolder extends RecyclerView.ViewHolder {
        ImageView post_imagev,like_imagev,comment_imagev,share_imagev;
        CircleImageView user_imagev;
        TextView username_textv,like_count_textv,comment_count_textv;


        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);

            user_imagev=itemView.findViewById(R.id.user_imageview);
            username_textv=itemView.findViewById(R.id.username_textview);
            post_imagev=itemView.findViewById(R.id.post_imageview);
            like_count_textv=itemView.findViewById(R.id.like_count_textview);
            comment_count_textv=itemView.findViewById(R.id.comment_count_textview);
            like_imagev=itemView.findViewById(R.id.like_imageview);

            comment_imagev=itemView.findViewById(R.id.comment_imageview);
            share_imagev=itemView.findViewById(R.id.share_imageview);
        }
    }
}

