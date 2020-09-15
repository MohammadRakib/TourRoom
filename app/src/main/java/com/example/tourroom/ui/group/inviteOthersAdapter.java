package com.example.tourroom.ui.group;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class inviteOthersAdapter extends RecyclerView.Adapter<inviteOthersAdapter.ViewHolder> {
    @NonNull
    @Override
    public inviteOthersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
            View view=layoutInflater.inflate(R.layout.invite_others_row,parent,false);
            inviteOthersAdapter.ViewHolder viewHolder=new inviteOthersAdapter.ViewHolder(view);

            return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull inviteOthersAdapter.ViewHolder holder, int position) {

        holder.usernameInvite.setText("Jarin");
        holder.usernameInvite.setText("Tasnim");
        holder.usernameInvite.setText("Rakib");
        holder.usernameInvite.setText("Dhrubojit");

    }

    @Override
    public int getItemCount() {
        return 4;
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
