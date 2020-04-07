package com.example.tourroom.ui.noti_fication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;

public class general_notification_adapter extends RecyclerView.Adapter<general_notification_adapter.general_notification_viewholder> {

    @NonNull
    @Override
    public general_notification_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.general_notification_row,parent,false);
        return new general_notification_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull general_notification_viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class general_notification_viewholder extends RecyclerView.ViewHolder{

        ImageView general_notification_img;
        TextView general_notification_text_view;

        public general_notification_viewholder(@NonNull View itemView) {
            super(itemView);
            general_notification_img=itemView.findViewById(R.id.invitation_notification_img_id);
            general_notification_text_view=itemView.findViewById(R.id.invitation_notification_textv_id);
        }
    }

}
