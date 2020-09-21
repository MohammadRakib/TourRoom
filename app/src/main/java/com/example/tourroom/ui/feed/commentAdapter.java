package com.example.tourroom.ui.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.Data.commentData;
import com.example.tourroom.R;

import java.util.List;

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.commentViewHolder>{
    List<commentData> commentDataList;
    Context context;

    public commentAdapter(List<commentData> commentDataList, Context context) {
        this.commentDataList = commentDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public commentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.commentrow,parent,false);
        return new commentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull commentViewHolder holder, int position) {
        commentData commentData = commentDataList.get(position);
        holder.userName.setText(commentData.getUserName());
        holder.commentText.setText(commentData.getCommentText());

    }

    @Override
    public int getItemCount() {
        return commentDataList.size();
    }

    static class commentViewHolder  extends RecyclerView.ViewHolder{

        TextView commentText, userName;

        public commentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.commentText);
            userName = itemView.findViewById(R.id.userName);
        }
    }

}
