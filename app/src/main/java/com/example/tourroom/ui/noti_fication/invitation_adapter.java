package com.example.tourroom.ui.noti_fication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;

public class invitation_adapter extends RecyclerView.Adapter<invitation_adapter.invitation_view_holder>{

    @NonNull
    @Override
    public invitation_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.invitation_row,parent,false);
        return new invitation_view_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull invitation_view_holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class invitation_view_holder extends RecyclerView.ViewHolder{

        ImageView invitation_notification_img;
        TextView invitation_notification_text_view;
        Button reject,join;

        public invitation_view_holder(@NonNull View itemView) {
            super(itemView);
            invitation_notification_img = itemView.findViewById(R.id.invitation_notification_img_id);
            invitation_notification_text_view = itemView.findViewById(R.id.invitation_notification_textv_id);
            reject = itemView.findViewById(R.id.reject);
            join = itemView.findViewById(R.id.join);
        }
    }
}
