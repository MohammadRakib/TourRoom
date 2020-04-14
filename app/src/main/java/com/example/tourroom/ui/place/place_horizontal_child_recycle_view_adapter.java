package com.example.tourroom.ui.place;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourroom.R;
import com.google.android.material.card.MaterialCardView;

public class place_horizontal_child_recycle_view_adapter extends RecyclerView.Adapter<place_horizontal_child_recycle_view_adapter.place_child_horizontal_recycle_view_viewholder>{


    public interface place_child_recycle_view_click_listener_interface {
        void on_Item_click(int position);
    }

    private place_child_recycle_view_click_listener_interface place_child_recycle_view_click_listener_interface;

    place_horizontal_child_recycle_view_adapter(place_child_recycle_view_click_listener_interface place_child_recycle_view_click_listener_interface) {
        this.place_child_recycle_view_click_listener_interface = place_child_recycle_view_click_listener_interface;
    }

    @NonNull
    @Override
    public place_child_horizontal_recycle_view_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.place_horizontal_child_recycle_view_row,parent,false);
        return new place_child_horizontal_recycle_view_viewholder(view,place_child_recycle_view_click_listener_interface);
    }

    @Override
    public void onBindViewHolder(@NonNull place_child_horizontal_recycle_view_viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class place_child_horizontal_recycle_view_viewholder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        ImageView horizontal_child_recycle_view_place_image;
        TextView horizontal_child_recycle_view_place_name;
        Button horizontal_child_recycle_view_add_visited_button;
        MaterialCardView place_horizontal_child_recycle_view_row_card;
        place_child_recycle_view_click_listener_interface place_child_recycle_view_click_listener_interface;

        place_child_horizontal_recycle_view_viewholder(@NonNull View itemView ,place_child_recycle_view_click_listener_interface place_child_recycle_view_click_listener_interface) {
            super(itemView);
            horizontal_child_recycle_view_place_image = itemView.findViewById(R.id.horizontal_recycle_view_place_image_id);
            horizontal_child_recycle_view_place_name = itemView.findViewById(R.id.horizontal_recycle_view_Place_name_text_view);
            horizontal_child_recycle_view_add_visited_button = itemView.findViewById(R.id.horizontal_recycle_view_add_visited_button);
            this.place_child_recycle_view_click_listener_interface = place_child_recycle_view_click_listener_interface;
            place_horizontal_child_recycle_view_row_card = itemView.findViewById(R.id.place_horizontal_child_recycle_view_row_card_id);
            place_horizontal_child_recycle_view_row_card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            place_child_recycle_view_click_listener_interface.on_Item_click(getAdapterPosition());
        }
    }

}
