package com.example.tourroom.ui.profile;

import android.app.ProgressDialog;
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
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.postdata;
import com.example.tourroom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;


public class Profile_Recycler_Adapter extends RecyclerView.Adapter {

    String currentUserID;
    profileInterface profileInterface;
    Context context;
    String userName, userImage;
    List<postdata> userPostList;
    int followerCount = 0;
    int followingCount = 0;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Profile_Recycler_Adapter(Context context, profileInterface profileInterface, String userName, String userImage, List<postdata> userPostList) {
        this.context = context;
        this.profileInterface = profileInterface;
        this.userName = userName;
        this.userImage = userImage;
        this.userPostList = userPostList;
        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
    }



    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return 0;
        }else{
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if(viewType == 0){
            view = layoutInflater.inflate(R.layout.profile_upper_part,parent,false);
            return new profileUpperPartViewHolder(view);
        }else {
            view = layoutInflater.inflate(R.layout.profile_row_items,parent,false);
            return new profileLowerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
          if(position == 0){

              final profileUpperPartViewHolder profileUpperPartViewHolder = (Profile_Recycler_Adapter.profileUpperPartViewHolder) holder;

              profileUpperPartViewHolder.name_textv.setText(userName);

                  Glide.with(context)
                          .load(userImage)
                          .format(DecodeFormat.PREFER_ARGB_8888)
                          .dontAnimate()
                          .placeholder(R.drawable.dummyavatar)
                          .into(profileUpperPartViewHolder.profile_imagev);

              profileUpperPartViewHolder.post_count_textv.setText(String.valueOf(userPostList.size()));

              profileUpperPartViewHolder.editProfileImage.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      profileInterface.onclickEditImage();
                  }
              });

            userFollowAndFollowerCount(profileUpperPartViewHolder);

          }else {
              final postdata postdata = userPostList.get(position-1);
              final profileLowerViewHolder profileLowerViewHolder = (Profile_Recycler_Adapter.profileLowerViewHolder) holder;
              profileLowerViewHolder.username_textv.setText(userName);
              Glide.with(context)
                      .load(userImage)
                      .format(DecodeFormat.PREFER_ARGB_8888)
                      .dontAnimate()
                      .placeholder(R.drawable.dummyavatar)
                      .into(profileLowerViewHolder.user_imagev);
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

                      getINSTANCE().getRootRef().child("post").child(currentUserID).child(postdata.getPostId()).child("likeNumber").setValue(String.valueOf(temp))
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
    }

    @Override
    public int getItemCount() {
        return userPostList.size()+1;
    }

    static class profileUpperPartViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profile_imagev;
        TextView name_textv,post_textv,followers_textv,following_textv,post_count_textv,followers_count_textv,following_count_textv;
        AppCompatImageButton editProfileImage;
        public profileUpperPartViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_imagev=itemView.findViewById(R.id.profile_imageview);
            name_textv=itemView.findViewById(R.id.profilename_textview);
            post_textv=itemView.findViewById(R.id.post_textview);
            followers_textv=itemView.findViewById(R.id.followers_textview);
            following_textv=itemView.findViewById(R.id.following_textview);
            post_count_textv=itemView.findViewById(R.id.post_count_textview);
            followers_count_textv=itemView.findViewById(R.id.follower_count_textview);
            following_count_textv=itemView.findViewById(R.id.following_count_textview);
            editProfileImage = itemView.findViewById(R.id.editProfileImage);

        }
    }

    static class profileLowerViewHolder extends RecyclerView.ViewHolder {
        ImageView post_imagev,like_imagev,comment_imagev,share_imagev;
        CircleImageView user_imagev;
        TextView username_textv,like_count_textv,comment_count_textv;


        public profileLowerViewHolder(@NonNull View itemView) {
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

    public void userFollowAndFollowerCount(final profileUpperPartViewHolder profileUpperPartViewHolder){

        getINSTANCE().getRootRef().child("UserFollower").child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    String dummy = data.getKey();
                    followerCount++;
                }
                profileUpperPartViewHolder.followers_count_textv.setText(String.valueOf(followerCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getINSTANCE().getRootRef().child("UserFollowing").child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()){
                    String dummy = data.getKey();
                    followingCount++;
                }
                profileUpperPartViewHolder.following_count_textv.setText(String.valueOf(followingCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
