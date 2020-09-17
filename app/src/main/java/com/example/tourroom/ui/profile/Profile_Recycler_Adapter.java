package com.example.tourroom.ui.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile_Recycler_Adapter extends RecyclerView.Adapter {

   profileInterface profileInterface;
    Context context;
    String userName, userImage;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Profile_Recycler_Adapter(Context context, profileInterface profileInterface, String userName, String userImage) {
        this.context = context;
        this.profileInterface = profileInterface;
        this.userName = userName;
        this.userImage = userImage;

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

              profileUpperPartViewHolder profileUpperPartViewHolder = (Profile_Recycler_Adapter.profileUpperPartViewHolder) holder;

              profileUpperPartViewHolder.name_textv.setText(userName);

                  Glide.with(context)
                          .load(userImage)
                          .format(DecodeFormat.PREFER_ARGB_8888)
                          .dontAnimate()
                          .placeholder(R.drawable.dummyavatar)
                          .into(profileUpperPartViewHolder.profile_imagev);

              profileUpperPartViewHolder.following_count_textv.setText("10");

              profileUpperPartViewHolder.editProfileImage.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      profileInterface.onclickEditImage();
                  }
              });

          }else {
              profileLowerViewHolder profileLowerViewHolder = (Profile_Recycler_Adapter.profileLowerViewHolder) holder;

          }
    }

    @Override
    public int getItemCount() {
        return 20;
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
