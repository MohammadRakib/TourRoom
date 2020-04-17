package com.example.tourroom.ui.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;

public class RecyclerAdapterForEvent extends RecyclerView.Adapter<RecyclerAdapterForEvent.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_itemforevent,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.messageforannouncementtextview.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageViewidforevent;
        TextView messageforannouncementtextview,eventdatetextView_forevent,eventdaytextView_forevent,journeystarttextView_forevent;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewidforevent=itemView.findViewById(R.id.imageViewid_forevent);
            messageforannouncementtextview=itemView.findViewById(R.id.messageforannouncementtextviewid);
            eventdatetextView_forevent=itemView.findViewById(R.id.eventdatetextViewid_forevent);
            eventdaytextView_forevent=itemView.findViewById(R.id.eventdaytextViewid_forevent);
            journeystarttextView_forevent=itemView.findViewById(R.id.journeystarttextViewid_forevent);

        }


    }
}

