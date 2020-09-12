package com.example.tourroom.ui.group;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.group_data;
import com.example.tourroom.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;


public class RecyclerAdapterForHorizontalScroll extends RecyclerView.Adapter< RecyclerAdapterForHorizontalScroll.ViewHolder> {

    private HRecyclerViewClickInterface hRecyclerViewClickInterface;
    private List<group_data> groupDataList;
    private Context context;


    public RecyclerAdapterForHorizontalScroll(Context context, HRecyclerViewClickInterface hRecyclerViewClickInterface, List<group_data> groupDataList) {
        this.context = context;
        this.hRecyclerViewClickInterface = hRecyclerViewClickInterface;
        this.groupDataList = groupDataList;
    }



    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.group_horizontal_child_recycle_view_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        group_data group_data = groupDataList.get(position);
        holder.suggestgroupName_horizontal_textview.setText(group_data.getGroupName());
        if(group_data.getGroupImage() != null){
            Glide.with(context)
                    .load(group_data.getGroupImage())
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .dontAnimate()
                    .placeholder(R.drawable.dummyimage)
                    .into(holder.suggestgroup_horizontal_imageview);
        }


    }

    @Override
    public int getItemCount() {
        return groupDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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



