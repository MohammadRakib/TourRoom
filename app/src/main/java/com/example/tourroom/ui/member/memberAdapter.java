package com.example.tourroom.ui.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.User_Data;
import com.example.tourroom.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class memberAdapter extends RecyclerView.Adapter<memberAdapter.ViewHolder>{

    List<User_Data> groupMemberList;
    Context context;
    memberInterface memberInterface;

    public memberAdapter(List<User_Data> groupMemberList, Context context, memberInterface memberInterface) {
        this.groupMemberList = groupMemberList;
        this.context = context;
        this.memberInterface = memberInterface;
    }

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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
      final User_Data user_data = groupMemberList.get(position);
      holder.usernamermember.setText(user_data.getName());

        Glide.with(context)
                .load(user_data.getImage())
                .format(DecodeFormat.PREFER_ARGB_8888)
                .dontAnimate()
                .placeholder(R.drawable.dummyavatar)
                .into(holder.userImagermember);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberInterface.memberClick(position,user_data.getUid());
            }
        });

    }

    @Override
    public int getItemCount() {
        return groupMemberList.size();
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

