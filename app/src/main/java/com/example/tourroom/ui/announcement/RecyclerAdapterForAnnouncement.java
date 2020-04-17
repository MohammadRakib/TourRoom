package com.example.tourroom.ui.announcement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;

public class RecyclerAdapterForAnnouncement extends RecyclerView.Adapter<RecyclerAdapterForAnnouncement.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_itemforannouncements,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.messageforannouncement_textviewid.setText(R.string.sundarban);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView messageforannouncement_textviewid;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            messageforannouncement_textviewid=itemView.findViewById(R.id.messageforannouncementtextviewid);


        }


    }
}

