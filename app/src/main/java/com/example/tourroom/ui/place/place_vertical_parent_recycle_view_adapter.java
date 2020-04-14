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
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;
import com.google.android.material.card.MaterialCardView;

import java.util.Objects;

public class place_vertical_parent_recycle_view_adapter extends RecyclerView.Adapter implements place_horizontal_child_recycle_view_adapter.place_child_recycle_view_click_listener_interface {
    Context context;
    NavController navController;

    public interface place_parent_recycle_view_click_listener_interface {
        void on_Item_click(int position);
        void on_add_button_click();
    }

    private place_parent_recycle_view_click_listener_interface place_parent_recycle_view_click_listener_interface;


    //child recycle view onclick
    @Override
    public void on_Item_click(int position) {
          place_parent_recycle_view_click_listener_interface.on_Item_click(position);
        Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();

    }

    place_vertical_parent_recycle_view_adapter(place_parent_recycle_view_click_listener_interface place_parent_recycle_view_click_listener_interface) {
        this.place_parent_recycle_view_click_listener_interface = place_parent_recycle_view_click_listener_interface;
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0){
            return 0;
        }else if (position == 1){
            return 1;
        }
       return 2;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if(viewType == 0){
            view = layoutInflater.inflate(R.layout.place_vertical_parent_recycle_view_row_0,parent,false);
            return new row_0_view_holder(view, place_parent_recycle_view_click_listener_interface);
        }else if(viewType == 1){
            view = layoutInflater.inflate(R.layout.place_vertical_parent_recycle_view_row_1,parent,false);
            return new row_1_view_holder(view);
        }
        view = layoutInflater.inflate(R.layout.place_vertical_parent_recycle_view_row_2,parent,false);
        return new row_2_view_holder(view, place_parent_recycle_view_click_listener_interface);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
       context=holder.itemView.getContext();
       if (position == 0){//view 0
           row_0_view_holder row_0_view_holder = (place_vertical_parent_recycle_view_adapter.row_0_view_holder) holder;


       }else if (position == 1){//view 1

           place_horizontal_child_recycle_view_adapter place_horizontal_child_recycle_view_adapter = new place_horizontal_child_recycle_view_adapter(this);
           row_1_view_holder row_1_view_holder = (place_vertical_parent_recycle_view_adapter.row_1_view_holder) holder;
           row_1_view_holder.horizontal_child_recycle_view.setAdapter(place_horizontal_child_recycle_view_adapter);

       }else {//view 2
           row_2_view_holder row_2_view_holder = (place_vertical_parent_recycle_view_adapter.row_2_view_holder) holder;
       }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    static class row_0_view_holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView add_new_place_text_view;
        AppCompatImageButton add_new_place_button;
        place_parent_recycle_view_click_listener_interface place_parent_recycle_view_click_listener_interface;

        row_0_view_holder(@NonNull final View itemView , place_parent_recycle_view_click_listener_interface place_parent_recycle_view_click_listener_interface) {
            super(itemView);
            add_new_place_text_view = itemView.findViewById(R.id.add_new_place_text_view_id);
            add_new_place_button = itemView.findViewById(R.id.add_new_place_button);
            this.place_parent_recycle_view_click_listener_interface = place_parent_recycle_view_click_listener_interface;
            //add button onclick
            add_new_place_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            place_parent_recycle_view_click_listener_interface.on_add_button_click();
        }
    }

    static class row_1_view_holder extends RecyclerView.ViewHolder{

        TextView recommend_place_text_view, tour_place_text_view;
        RecyclerView horizontal_child_recycle_view;

        row_1_view_holder(@NonNull View itemView) {
            super(itemView);
            recommend_place_text_view = itemView.findViewById(R.id.recommended_place_text_view);
            tour_place_text_view = itemView.findViewById(R.id.tour_places_text_view);
            horizontal_child_recycle_view = itemView.findViewById(R.id.place_child_horizontal_recycle_view_id);
        }
    }

    static class row_2_view_holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView  vertical_recycle_view_place_image;
        TextView vertical_recycle_view_place_name;
        Button vertical_recycle_view_add_visited_button;
        MaterialCardView place_vertical_parent_recycle_view_row_card;
        place_parent_recycle_view_click_listener_interface place_parent_recycle_view_click_listener_interface;

        row_2_view_holder(@NonNull View itemView, place_parent_recycle_view_click_listener_interface place_parent_recycle_view_click_listener_interface){
            super(itemView);
            vertical_recycle_view_place_image = itemView.findViewById(R.id.vertical_recyle_view_place_image_id);
            vertical_recycle_view_place_name = itemView.findViewById(R.id.vertical_recycle_view_Place_name_text_view);
            vertical_recycle_view_add_visited_button = itemView.findViewById(R.id.vertical_recycle_view_add_visited_button);
            place_vertical_parent_recycle_view_row_card = itemView.findViewById(R.id.place_vertical_parent_recycle_view_row_card_id);
            this.place_parent_recycle_view_click_listener_interface = place_parent_recycle_view_click_listener_interface;
            //material card view onclick
            place_vertical_parent_recycle_view_row_card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           place_parent_recycle_view_click_listener_interface.on_Item_click(getAdapterPosition());
        }
    }
}
