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

import static com.example.tourroom.ui.group.invite_other_activity.CheckList;
import static com.example.tourroom.ui.member.Member_Requests_fragment.CheckListForMemberRequest;

public class memeberRequestAdapter extends RecyclerView.Adapter<memeberRequestAdapter.ViewHolder> {

    boolean checkAll;
    List<User_Data> memberRequestList;
    Context context;

    public memeberRequestAdapter(List<User_Data> memberRequestList, boolean checkAll , Context context) {
        this.memberRequestList = memberRequestList;
        this.checkAll = checkAll;
        this.context = context;
    }

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
    public void onBindViewHolder(@NonNull final memeberRequestAdapter.ViewHolder holder, int position) {
        final User_Data user_data = memberRequestList.get(position);

        Glide.with(context)
                .load(user_data.getImage())
                .format(DecodeFormat.PREFER_ARGB_8888)
                .dontAnimate()
                .placeholder(R.drawable.dummyavatar)
                .into(holder.userImagerequest);

        holder.usernamerequest.setText(user_data.getName());

        if(checkAll){
            holder.requestCheckBox.setChecked(true);
            CheckListForMemberRequest.add(user_data.getUid());
        }

        holder.requestCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.requestCheckBox.isChecked()){
                    CheckListForMemberRequest.add(user_data.getUid());
                }else {
                    CheckListForMemberRequest.remove(user_data.getUid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberRequestList.size();
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


