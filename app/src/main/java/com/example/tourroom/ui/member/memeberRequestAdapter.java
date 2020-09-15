package com.example.tourroom.ui.member;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class memeberRequestAdapter extends RecyclerView.Adapter<memeberRequestAdapter.ViewHolder> {
    @NonNull
    @Override
    public memeberRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.member_request_row,parent,false);
      memeberRequestAdapter.ViewHolder viewHolder;
        viewHolder = new memeberRequestAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull memeberRequestAdapter.ViewHolder holder, int position) {
        holder.usernamerequest.setText("Jarin");
        holder.usernamerequest.setText("Tasnim");
        holder.usernamerequest.setText("Rakib");
        holder.usernamerequest.setText("Dhrubojit");

    }

    @Override
    public int getItemCount() {
        return 4;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView userImagerequest;
        TextView usernamerequest;
        CheckBox requestCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImagerequest = itemView.findViewById(R.id.memberRequest_userImage);
            usernamerequest = itemView.findViewById(R.id.userName_memberrequest);
            requestCheckBox = itemView.findViewById(R.id.memberrequest_CheckBox);

        }
    }
}


