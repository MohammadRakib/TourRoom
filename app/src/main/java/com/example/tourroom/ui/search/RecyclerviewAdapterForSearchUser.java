package com.example.tourroom.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.User_Data;
import com.example.tourroom.Data.group_data;
import com.example.tourroom.R;
import com.example.tourroom.ui.member.memberAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerviewAdapterForSearchUser extends RecyclerView.Adapter<RecyclerviewAdapterForSearchUser.ViewHolder>{

    public interface search_data_get_user__interface {
        void on_Item_click_user(int position, User_Data user_Data);
    }

    List<User_Data> usersList;
    Context context;
    search_data_get_user__interface search_data_get_user__interface;

    public RecyclerviewAdapterForSearchUser(List<User_Data> usersList, Context context,
                                            search_data_get_user__interface search_data_get_user__interface) {
        this.usersList =usersList;
        this.context = context;
        this.search_data_get_user__interface = search_data_get_user__interface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.member_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final User_Data user_data = usersList.get(position);
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
                search_data_get_user__interface.on_Item_click_user(position,user_data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
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
