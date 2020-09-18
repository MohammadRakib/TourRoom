package com.example.tourroom.ui.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class Otherprofile_Recycler_Adapter extends RecyclerView.Adapter {

    Context context;
    String userName, userImage;

    public Otherprofile_Recycler_Adapter(Context context, String userName, String userImage) {
        this.context = context;
        this.userName = userName;
        this.userImage = userImage;
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
            Otherprofile_Recycler_Adapter.otherprofileUpperPartViewHolder profileUpperPartViewHolder = (Otherprofile_Recycler_Adapter.otherprofileUpperPartViewHolder) holder;
            profileUpperPartViewHolder.name_textv.setText(userName);

            Glide.with(context)
                    .load(userImage)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .dontAnimate()
                    .placeholder(R.drawable.dummyavatar)
                    .into(profileUpperPartViewHolder.profile_imagev);


        }else {
            Otherprofile_Recycler_Adapter.otherprofileLowerViewHolder profileLowerViewHolder = (Otherprofile_Recycler_Adapter.otherprofileLowerViewHolder) holder;

        }
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