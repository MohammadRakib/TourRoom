package com.example.tourroom.ui.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.User_Data;
import com.example.tourroom.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tourroom.ui.group.invite_other_activity.CheckList;

public class inviteOthersAdapter extends RecyclerView.Adapter<inviteOthersAdapter.ViewHolder> {

    List<User_Data> inviteOtherList;
    Context context;
    boolean checkAll;


    public inviteOthersAdapter(List<User_Data> inviteOtherList, boolean checkAll , Context context) {
        this.inviteOtherList = inviteOtherList;
        this.checkAll = checkAll;
        this.context = context;
    }

    @NonNull
    @Override
    public inviteOthersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            View view=layoutInflater.inflate(R.layout.invite_others_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final inviteOthersAdapter.ViewHolder holder, int position) {

        final User_Data user_data = inviteOtherList.get(position);

        Glide.with(context)
                .load(user_data.getImage())
                .format(DecodeFormat.PREFER_ARGB_8888)
                .dontAnimate()
                .placeholder(R.drawable.dummyavatar)
                .into(holder.userImageInvite);

        holder.usernameInvite.setText(user_data.getName());

        if(checkAll){
            holder.InviteCheckBox.setChecked(true);
            CheckList.add(user_data.getUid());
        }

        holder.InviteCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.InviteCheckBox.isChecked()){
                  CheckList.add(user_data.getUid());
                }else {
                    CheckList.remove(user_data.getUid());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return inviteOtherList.size();
    }
        public static class ViewHolder extends RecyclerView.ViewHolder{
            CircleImageView userImageInvite;
            TextView usernameInvite;
            CheckBox InviteCheckBox;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                userImageInvite = itemView.findViewById(R.id.inviteOther_userImage);
                usernameInvite = itemView.findViewById(R.id.userName_inviteOthers);
                InviteCheckBox = itemView.findViewById(R.id.inviterOthersCheckBox);

            }
        }
}
