package com.example.tourroom.ui.Poll;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.Data.Read_poll_data;
import com.example.tourroom.R;

import java.util.List;

public class RecyclerAdapterForCreatePoll extends RecyclerView.Adapter<RecyclerAdapterForCreatePoll.ViewHolder> {


    List<Read_poll_data> pollDataList;
    Context context;

    public RecyclerAdapterForCreatePoll(List<Read_poll_data> pollDataList, Context context) {
        this.pollDataList = pollDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_itemforcreatepoll, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       /* final Read_poll_data pollData =pollDataList.get(position);
        holder.opbtn1.setText(pollData.getOptionName1());
        holder.opbtn2.setText(pollData.getOptionName2());
        holder.optex1.setText(pollData.getOption1Vote());
        holder.optex2.setText(pollData.getOption2Vote());*/
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
          Button opbtn1,opbtn2;
          TextView optex1,optex2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

           opbtn1=itemView.findViewById(R.id.option1Button);
           opbtn2=itemView.findViewById(R.id.option2Button);
           optex1=itemView.findViewById(R.id.option1TextView);
           optex2=itemView.findViewById(R.id.option2TextView);

        }


    }
}
