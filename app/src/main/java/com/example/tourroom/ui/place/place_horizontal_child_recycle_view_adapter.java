package com.example.tourroom.ui.place;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.place_data;
import com.example.tourroom.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class place_horizontal_child_recycle_view_adapter extends RecyclerView.Adapter<place_horizontal_child_recycle_view_adapter.place_child_horizontal_recycle_view_viewholder>{


    public interface place_child_recycle_view_click_listener_interface {
        void on_Item_click(int position,place_data placeData);
    }

    private place_child_recycle_view_click_listener_interface place_child_recycle_view_click_listener_interface;
    private static List<place_data> horizontalPlaceList;
    private Context context;

    place_horizontal_child_recycle_view_adapter(Context context,place_child_recycle_view_click_listener_interface place_child_recycle_view_click_listener_interface,List<place_data> horizontalPlaceList) {
        this.context=context;
        this.place_child_recycle_view_click_listener_interface = place_child_recycle_view_click_listener_interface;
        this.horizontalPlaceList=horizontalPlaceList;
    }

    @NonNull
    @Override
    public place_child_horizontal_recycle_view_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.place_horizontal_child_recycle_view_row,parent,false);
        return new place_child_horizontal_recycle_view_viewholder(view,place_child_recycle_view_click_listener_interface);
    }

    @Override
    public void onBindViewHolder(@NonNull place_child_horizontal_recycle_view_viewholder holder, final int position) {
        final place_data placeData = horizontalPlaceList.get(position);
        holder.horizontal_child_recycle_view_place_name.setText(placeData.getPlaceName());
        if(placeData.getPlaceImage() != null){
            Glide.with(context)
                    .load(placeData.getPlaceImage())
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .dontAnimate()
                    .placeholder(R.drawable.dummyimage)
                    .into(holder.horizontal_child_recycle_view_place_image);
        }
        holder.place_horizontal_child_recycle_view_row_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                place_child_recycle_view_click_listener_interface.on_Item_click(position,placeData);
            }
        });

    }

    @Override
    public int getItemCount() {
       return horizontalPlaceList.size();


    }

    static class place_child_horizontal_recycle_view_viewholder extends RecyclerView.ViewHolder  {

        ImageView horizontal_child_recycle_view_place_image;
        TextView horizontal_child_recycle_view_place_name;
        MaterialCardView place_horizontal_child_recycle_view_row_card;

        place_child_horizontal_recycle_view_viewholder(@NonNull View itemView ,place_child_recycle_view_click_listener_interface place_child_recycle_view_click_listener_interface) {
            super(itemView);
            horizontal_child_recycle_view_place_image = itemView.findViewById(R.id.horizontal_recycle_view_place_image_id);
            horizontal_child_recycle_view_place_name = itemView.findViewById(R.id.horizontal_recycle_view_Place_name_text_view);
            place_horizontal_child_recycle_view_row_card = itemView.findViewById(R.id.place_horizontal_child_recycle_view_row_card_id);

        }

    }
}
