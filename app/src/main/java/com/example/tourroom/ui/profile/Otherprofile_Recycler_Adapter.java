package com.example.tourroom.ui.profile;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static java.util.Objects.requireNonNull;


public class Otherprofile_Recycler_Adapter extends RecyclerView.Adapter {

    private final String currentUser;
    Context context;
    String userName, userImage,UserId;
    boolean ifFollowing;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Otherprofile_Recycler_Adapter(Context context, String userName, String userImage, String UserId, boolean ifFollowing) {
        this.context = context;
        this.userName = userName;
        this.userImage = userImage;
        this.UserId = UserId;
        this.ifFollowing = ifFollowing;
        currentUser = requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if(viewType == 0){
            view = layoutInflater.inflate(R.layout.otherprofile_upperpart,parent,false);
            return new otherprofileUpperPartViewHolder(view);
        }else {
            view = layoutInflater.inflate(R.layout.otherprofile_row_items,parent,false);
            return new otherprofileLowerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(position == 0){
            final Otherprofile_Recycler_Adapter.otherprofileUpperPartViewHolder profileUpperPartViewHolder = (Otherprofile_Recycler_Adapter.otherprofileUpperPartViewHolder) holder;
            profileUpperPartViewHolder.name_textv.setText(userName);

            Glide.with(context)
                    .load(userImage)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .dontAnimate()
                    .placeholder(R.drawable.dummyavatar)
                    .into(profileUpperPartViewHolder.profile_imagev);


            if(currentUser.equals(UserId)){
                profileUpperPartViewHolder.followButton.setVisibility(View.INVISIBLE);
            }else if(ifFollowing){
                profileUpperPartViewHolder.followButton.setVisibility(View.INVISIBLE);
            }else {
                profileUpperPartViewHolder.followButton.setVisibility(View.VISIBLE);
            }

            profileUpperPartViewHolder.followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    followTheUser(profileUpperPartViewHolder.followButton);
                }
            });

        }else {
            Otherprofile_Recycler_Adapter.otherprofileLowerViewHolder profileLowerViewHolder = (Otherprofile_Recycler_Adapter.otherprofileLowerViewHolder) holder;

        }
    }

    private void followTheUser(final Button followButton) {

        final Map<String, Object> update = new HashMap<>(); //this hashmap is used to write different data in different path in the database at once or atomically
        update.put("UserFollowing/"+currentUser+"/"+UserId,true);
        update.put("UserFollower/"+UserId+"/"+currentUser,true);


        getINSTANCE().getRootRef().updateChildren(update).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                followButton.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "Followed the User", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Could not follow,try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    static class otherprofileUpperPartViewHolder extends RecyclerView.ViewHolder{

        CircleImageView profile_imagev;
        TextView name_textv,post_textv,followers_textv,following_textv,post_count_textv,followers_count_textv,following_count_textv;
        Button followButton;


        public otherprofileUpperPartViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_imagev=itemView.findViewById(R.id.Other_profile_imageview);
            name_textv=itemView.findViewById(R.id.Other_profilename_textview);
            post_textv=itemView.findViewById(R.id.Other_post_textview);
            followers_textv=itemView.findViewById(R.id.Other_followers_textview);
            following_textv=itemView.findViewById(R.id.Other_following_textview);
            post_count_textv=itemView.findViewById(R.id.Other_post_count_textview);
            followers_count_textv=itemView.findViewById(R.id.Other_follower_count_textview);
            following_count_textv=itemView.findViewById(R.id.Other_following_count_textview);
            followButton = itemView.findViewById(R.id.followButton);
        }
    }

    static class otherprofileLowerViewHolder extends RecyclerView.ViewHolder {
        ImageView user_imagev,post_imagev,like_imagev,comment_imagev,share_imagev;
        TextView username_textv,post_textv,like_count_textv,comment_count_textv;
        public otherprofileLowerViewHolder(@NonNull View itemView) {
            super(itemView);

            user_imagev=itemView.findViewById(R.id.user_imageview);
            username_textv=itemView.findViewById(R.id.username_textview);
            post_textv=itemView.findViewById(R.id.post_textview);
            post_imagev=itemView.findViewById(R.id.post_imageview);
            like_count_textv=itemView.findViewById(R.id.like_count_textview);
            comment_count_textv=itemView.findViewById(R.id.comment_count_textview);
            like_imagev=itemView.findViewById(R.id.like_imageview);
            comment_imagev=itemView.findViewById(R.id.comment_imageview);
            share_imagev=itemView.findViewById(R.id.share_imageview);
        }
    }
}