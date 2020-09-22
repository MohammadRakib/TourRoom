package com.example.tourroom.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.group_data;
import com.example.tourroom.Data.place_data;
import com.example.tourroom.R;
import com.example.tourroom.ui.group.group_vertical_parent_recycle_view_adapter;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerviewAdapterForSearchGroup extends RecyclerView.Adapter<RecyclerviewAdapterForSearchGroup.ViewHolder> {

    public interface search_data_get_group__interface {
        void on_Item_click_group(int position,group_data groupData);
    }

    List<group_data> groupDataList;
    Context context;
    search_data_get_group__interface search_data_get_group__interface;


    public RecyclerviewAdapterForSearchGroup(List<group_data> groupDataList, Context context, RecyclerviewAdapterForSearchGroup.search_data_get_group__interface search_data_get_group__interface) {
        this.groupDataList = groupDataList;
        this.context = context;
        this.search_data_get_group__interface = search_data_get_group__interface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        view = layoutInflater.inflate(R.layout.grouprow,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final group_data groupData = groupDataList.get(position);
        holder.vertical_recycle_view_group_name.setText(groupData.getGroupName());
        if(groupData.getGroupImage() != null){

            Glide.with(context)
                    .load(groupData.getGroupImage())
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .dontAnimate()
                    .placeholder(R.drawable.dummyimage)
                    .into(holder.vertical_recycle_view_group_image);
        }
        holder.group_vertical_parent_recycle_view_row_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_data_get_group__interface.on_Item_click_group(position,groupData);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupDataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView vertical_recycle_view_group_image;
        TextView vertical_recycle_view_group_name;
        MaterialCardView group_vertical_parent_recycle_view_row_card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vertical_recycle_view_group_image = itemView.findViewById(R.id.loadgroupimageview);
            vertical_recycle_view_group_name = itemView.findViewById(R.id.loadgroupnametextview);
            group_vertical_parent_recycle_view_row_card = itemView.findViewById(R.id.group_vertical_parent_recycle_view_row_card_id);
        }
    }
}
