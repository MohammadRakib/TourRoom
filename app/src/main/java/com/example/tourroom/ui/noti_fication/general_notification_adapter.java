package com.example.tourroom.ui.noti_fication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class general_notification_adapter extends RecyclerView.Adapter<general_notification_adapter.general_notification_viewholder> {

    List<String> notificationList;
    Context context;

    public general_notification_adapter(List<String> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public general_notification_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.general_notification_row,parent,false);
        return new general_notification_viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull general_notification_viewholder holder, int position) {
             holder.general_notification_text_view.setText(notificationList.get(position));
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    static class general_notification_viewholder extends RecyclerView.ViewHolder{

        TextView general_notification_text_view;

        public general_notification_viewholder(@NonNull View itemView) {
            super(itemView);
            general_notification_text_view=itemView.findViewById(R.id.invitation_notification_textv_id);
        }
    }

}
