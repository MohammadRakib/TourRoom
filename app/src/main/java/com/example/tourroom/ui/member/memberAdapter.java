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

public class memberAdapter extends RecyclerView.Adapter<memberAdapter.ViewHolder>{
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.member_row,parent,false);
        memberAdapter.ViewHolder viewHolder;
        viewHolder = new memberAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.usernamermember.setText("Jarin");
        holder.usernamermember.setText("Tasnim");
        holder.usernamermember.setText("Rakib");
        holder.usernamermember.setText("Dhrubojit");

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView userImagermember;
        TextView usernamermember;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userImagermember = itemView.findViewById(R.id.member_userImage);
            usernamermember = itemView.findViewById(R.id.userName_member);



        }
    }
}

