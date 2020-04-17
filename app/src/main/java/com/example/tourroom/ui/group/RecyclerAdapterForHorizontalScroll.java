package com.example.tourroom.ui.group;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;
import com.google.android.material.card.MaterialCardView;


public class RecyclerAdapterForHorizontalScroll extends RecyclerView.Adapter< RecyclerAdapterForHorizontalScroll.ViewHolder> {

    private HRecyclerViewClickInterface hRecyclerViewClickInterface;

    public RecyclerAdapterForHorizontalScroll(HRecyclerViewClickInterface hRecyclerViewClickInterface) {
        this.hRecyclerViewClickInterface = hRecyclerViewClickInterface;
    }



    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.group_horizontal_child_recycle_view_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.suggestgroupName_horizontal_textview.setText(String.valueOf(position));



    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView suggestgroup_horizontal_imageview;
        TextView suggestgroupName_horizontal_textview;
        MaterialCardView group_horizontal_child_recycle_view_row_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            suggestgroup_horizontal_imageview=itemView.findViewById(R.id.suggestedgroupimage);
            suggestgroupName_horizontal_textview=itemView.findViewById(R.id.suggestedgroupname);
            group_horizontal_child_recycle_view_row_card=itemView.findViewById(R.id.group_horizontal_child_recycle_view_row_card_id);

            group_horizontal_child_recycle_view_row_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hRecyclerViewClickInterface.onItemClickH(getAdapterPosition());
                }
            });


        }




        }
    }



