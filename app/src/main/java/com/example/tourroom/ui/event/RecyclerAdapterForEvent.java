package com.example.tourroom.ui.event;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.announcement_data;
import com.example.tourroom.Data.event_data;
import com.example.tourroom.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class RecyclerAdapterForEvent extends RecyclerView.Adapter<RecyclerAdapterForEvent.ViewHolder> {
    List<event_data> event_dataList;
    Context context;
    private final Calendar c = Calendar.getInstance(Locale.getDefault());
    public RecyclerAdapterForEvent( Context context,List<event_data> event_dataList) {
        this.context=context;
        this.event_dataList=event_dataList;
    }

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
        final event_data eventData =event_dataList.get(position);
        holder.messageforannouncementtextview.setText(eventData.getEventName());
        if(eventData.getEventPhoto()!= null){
            Glide.with(context)
                    .load(eventData.getEventPhoto())
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .dontAnimate()
                    .placeholder(R.drawable.dummyimage)
                    .into(holder.imageViewidforevent);
        }
        holder.eventdaytextView_forevent.setText(eventData.getEventDate());
        holder.journeystarttextView_forevent.setText(eventData.getEventStartTime());
        String time =eventData.getDateTime();
        Long tm =Long.valueOf(time);
        c.setTimeInMillis(tm*1000);
        String date_time =  DateFormat.format("MMM d yyyy, h:mm a",c).toString();
        holder.datetimeText.setText(date_time);
    }

    @Override
    public int getItemCount() {
        return event_dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageViewidforevent;
        TextView messageforannouncementtextview,eventdaytextView_forevent,journeystarttextView_forevent,datetimeText;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewidforevent=itemView.findViewById(R.id.imageViewid_forevent);
            messageforannouncementtextview=itemView.findViewById(R.id.messageforannouncementtextviewid);
            eventdaytextView_forevent=itemView.findViewById(R.id.eventdaytextViewid_forevent);
            journeystarttextView_forevent=itemView.findViewById(R.id.journeystarttextViewid_forevent);
            datetimeText=itemView.findViewById(R.id.dateTimeTextView);
        }


    }
}

