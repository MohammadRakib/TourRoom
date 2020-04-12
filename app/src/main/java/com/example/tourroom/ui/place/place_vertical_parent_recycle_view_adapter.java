package com.example.tourroom.ui.place;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;

public class place_vertical_parent_recycle_view_adapter extends RecyclerView.Adapter {


    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            return 0;
        }
        return 1;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if(viewType == 0){
            view = layoutInflater.inflate(R.layout.place_vertical_parent_recycle_view_row_1,parent,false);
            return new row_1_view_holder(view);
        }

        view = layoutInflater.inflate(R.layout.place_vertical_parent_recycle_view_row_2,parent,false);
        return new row_2_view_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       if (position == 0){
           place_horizontal_child_recycle_view_adapter place_horizontal_child_recycle_view_adapter = new place_horizontal_child_recycle_view_adapter();
           row_1_view_holder row_1_view_holder = (place_vertical_parent_recycle_view_adapter.row_1_view_holder) holder;
           row_1_view_holder.horizontal_child_recycle_view.setAdapter(place_horizontal_child_recycle_view_adapter);
       }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class row_1_view_holder extends RecyclerView.ViewHolder{

        TextView add_new_place_text_view, recommend_place_text_view, tour_place_text_view;
        AppCompatImageButton add_new_place_button;
        RecyclerView horizontal_child_recycle_view;

        public row_1_view_holder(@NonNull View itemView) {
            super(itemView);
            add_new_place_text_view = itemView.findViewById(R.id.add_new_place_text_view_id);
            recommend_place_text_view = itemView.findViewById(R.id.recommended_place_text_view);
            tour_place_text_view = itemView.findViewById(R.id.tour_places_text_view);
            add_new_place_button = itemView.findViewById(R.id.add_new_place_button);
            horizontal_child_recycle_view = itemView.findViewById(R.id.place_child_horizontal_recycle_view_id);
        }
    }

    class row_2_view_holder extends RecyclerView.ViewHolder{

        ImageView  vertical_recycle_view_place_image;
        TextView vertical_recycle_view_place_name;
        Button vertical_recycle_view_add_visited_button;


        public row_2_view_holder(@NonNull View itemView) {
            super(itemView);
            vertical_recycle_view_place_image = itemView.findViewById(R.id.vertical_recyle_view_place_image_id);
            vertical_recycle_view_place_name = itemView.findViewById(R.id.vertical_recycle_view_Place_name_text_view);
            vertical_recycle_view_add_visited_button = itemView.findViewById(R.id.vertical_recycle_view_add_visited_button);
        }
    }

}
