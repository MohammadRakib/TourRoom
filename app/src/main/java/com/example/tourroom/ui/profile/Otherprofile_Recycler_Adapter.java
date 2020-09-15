package com.example.tourroom.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;

public class Otherprofile_Recycler_Adapter extends RecyclerView.Adapter<Otherprofile_Recycler_Adapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.otherprofile_row_items,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView user_imagev,post_imagev,like_imagev,comment_imagev,share_imagev;
        TextView username_textv,post_textv,like_count_textv,comment_count_textv,like_textv,comment_textv,share_textv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_imagev=itemView.findViewById(R.id.user_imageview);
            username_textv=itemView.findViewById(R.id.username_textview);
            post_textv=itemView.findViewById(R.id.post_textview);
            post_imagev=itemView.findViewById(R.id.post_imageview);
            like_count_textv=itemView.findViewById(R.id.like_count_textview);
            comment_count_textv=itemView.findViewById(R.id.comment_count_textview);
            like_imagev=itemView.findViewById(R.id.like_imageview);
            like_textv=itemView.findViewById(R.id.like_textview);
            comment_imagev=itemView.findViewById(R.id.comment_imageview);
            comment_textv=itemView.findViewById(R.id.comment_textview);
            share_imagev=itemView.findViewById(R.id.share_imageview);
            share_textv=itemView.findViewById(R.id.share_textview);
        }
    }
}