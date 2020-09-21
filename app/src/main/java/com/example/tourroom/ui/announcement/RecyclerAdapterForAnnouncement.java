package com.example.tourroom.ui.announcement;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.Data.User_Data;
import com.example.tourroom.Data.announcement_data;
import com.example.tourroom.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapterForAnnouncement extends RecyclerView.Adapter<RecyclerAdapterForAnnouncement.ViewHolder> {




    List<announcement_data> announcement_dataList;
    Context context;
    private final Calendar c = Calendar.getInstance(Locale.getDefault());

    public RecyclerAdapterForAnnouncement( Context context,List<announcement_data> announcement_dataList) {
        this.context=context;
        this.announcement_dataList=announcement_dataList;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_itemforannouncements,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final announcement_data announcementData =announcement_dataList.get(position);

        //holder.messageforannouncement_textviewid.setText(R.string.sundarban);
        holder.messageforannouncement_textviewid.setText(announcementData.getAnnouncementText());
        String time =announcementData.getDate_time();
        Long tm =Long.valueOf(time);
        c.setTimeInMillis(tm*1000);
        String date_time =  DateFormat.format("MMM d yyyy, h:mm a",c).toString();
         holder.dateTime_textView.setText(date_time);
    }

    @Override
    public int getItemCount() {
        return announcement_dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView messageforannouncement_textviewid,dateTime_textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            messageforannouncement_textviewid=itemView.findViewById(R.id.messageforannouncementtextviewid);
            dateTime_textView=itemView.findViewById(R.id.dateTime);

        }
    }
}

