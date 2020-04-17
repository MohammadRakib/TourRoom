package com.example.tourroom.ui.Poll;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;


public class RecyclerAdapterForCreatePoll extends RecyclerView.Adapter<RecyclerAdapterForCreatePoll.ViewHolder> {

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

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
         RadioGroup radioGroup_forpoll;
         ProgressBar progressBar_option1_forpoll,progressBar_option2_forpoll;
         RadioButton option1radiobutton_forpoll,option2radiobutton_forpoll;
         TextView countvalueofoption1_forpoll,countvalueofoption2_forpoll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            radioGroup_forpoll=itemView.findViewById(R.id.radiogroupid_forpoll);
            progressBar_option1_forpoll=itemView.findViewById(R.id.option1_progressBar);
            progressBar_option2_forpoll=itemView.findViewById(R.id.option2_progressbar);
            option1radiobutton_forpoll=itemView.findViewById(R.id.option1_radiobutton);
            option2radiobutton_forpoll=itemView.findViewById(R.id.option2_radiobutton);
            countvalueofoption1_forpoll=itemView.findViewById(R.id.countvalueforoption1_textView);
            countvalueofoption2_forpoll=itemView.findViewById(R.id.countvalueofoption2_textview);
        }


    }
}
