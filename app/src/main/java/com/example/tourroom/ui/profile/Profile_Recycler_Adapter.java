package com.example.tourroom.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;


public class Profile_Recycler_Adapter extends RecyclerView.Adapter {
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
//        View view=layoutInflater.inflate(R.layout.profile_row_items,parent,false);
//        ViewHolder viewHolder=new ViewHolder(view);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//
//    }


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
            return new Profile_Recycler_Adapter.profileUpperPartViewHolder(view);
        }else {
            view = layoutInflater.inflate(R.layout.profile_row_items,parent,false);
            return new Profile_Recycler_Adapter.profileLowerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
          if(position == 0){
              profileUpperPartViewHolder profileUpperPartViewHolder = (Profile_Recycler_Adapter.profileUpperPartViewHolder) holder;
              profileUpperPartViewHolder.following_count_textv.setText("10");
          }else {
              profileLowerViewHolder profileLowerViewHolder = (Profile_Recycler_Adapter.profileLowerViewHolder) holder;

          }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class profileUpperPartViewHolder extends RecyclerView.ViewHolder{

        ImageView profile_imagev;
        TextView name_textv,post_textv,followers_textv,following_textv,post_count_textv,followers_count_textv,following_count_textv;
        public profileUpperPartViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_imagev=itemView.findViewById(R.id.profile_imageview);
            name_textv=itemView.findViewById(R.id.profilename_textview);
            post_textv=itemView.findViewById(R.id.post_textview);
            followers_textv=itemView.findViewById(R.id.followers_textview);
            following_textv=itemView.findViewById(R.id.following_textview);
            post_count_textv=itemView.findViewById(R.id.post_count_textview);
            followers_count_textv=itemView.findViewById(R.id.follower_count_textview);
            following_count_textv=itemView.findViewById(R.id.follower_count_textview);
        }
    }

    class profileLowerViewHolder extends RecyclerView.ViewHolder {
        ImageView user_imagev,post_imagev,like_imagev,comment_imagev,share_imagev;
        TextView username_textv,post_textv,like_count_textv,comment_count_textv;
        public profileLowerViewHolder(@NonNull View itemView) {
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
