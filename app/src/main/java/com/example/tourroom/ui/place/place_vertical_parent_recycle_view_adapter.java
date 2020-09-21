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
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.place_data;
import com.example.tourroom.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;

public class place_vertical_parent_recycle_view_adapter extends RecyclerView.Adapter implements place_horizontal_child_recycle_view_adapter.place_child_recycle_view_click_listener_interface {
    Context context;
    NavController navController;
    boolean flag=true;
    static  String randomPlaceTracker;
    String currentUser;

    private List<place_data> placeDataList;
    private List<place_data> RecommendedPlaceDataList;

    place_horizontal_child_recycle_view_adapter  place_horizontal_child_recycle_view_adapter;

    public interface place_parent_recycle_view_click_listener_interface {
        void on_Item_click(int position,place_data placeData);
        void on_add_button_click();
    }

    private place_parent_recycle_view_click_listener_interface place_parent_recycle_view_click_listener_interface;



    @Override
    public void on_Item_click(int position,place_data placeData) {

        place_parent_recycle_view_click_listener_interface.on_Item_click(position,placeData);
    }

    place_vertical_parent_recycle_view_adapter(Context context,place_parent_recycle_view_click_listener_interface place_parent_recycle_view_click_listener_interface,List<place_data> placeDataList) {
        this.context=context;
        this.place_parent_recycle_view_click_listener_interface = place_parent_recycle_view_click_listener_interface;
        this.placeDataList=placeDataList;

        RecommendedPlaceDataList=new ArrayList<>();
        currentUser=getINSTANCE().getMAuth().getCurrentUser().getUid();
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
            return new row_0_view_holder(view);
        }else if(viewType == 1){
            view = layoutInflater.inflate(R.layout.place_vertical_parent_recycle_view_row_1,parent,false);
            return new row_1_view_holder(view);
        }
        view = layoutInflater.inflate(R.layout.place_vertical_parent_recycle_view_row_2,parent,false);
        return new row_2_view_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        context=holder.itemView.getContext();
        if (position == 0){//view 0
            row_0_view_holder row_0_view_holder = (place_vertical_parent_recycle_view_adapter.row_0_view_holder) holder;
            row_0_view_holder.add_new_place_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    place_parent_recycle_view_click_listener_interface.on_add_button_click();
                }
            });


        }else if (position == 1){
            if(flag)
            {
                place_horizontal_child_recycle_view_adapter = new place_horizontal_child_recycle_view_adapter(context,this,RecommendedPlaceDataList);
                row_1_view_holder row_1_view_holder = (place_vertical_parent_recycle_view_adapter.row_1_view_holder) holder;
                row_1_view_holder.horizontal_child_recycle_view.setAdapter(place_horizontal_child_recycle_view_adapter);

                loadRecommenedPlaceList();
                flag=false;
            }



        }else if(position>1) {
            row_2_view_holder row_2_view_holder = (place_vertical_parent_recycle_view_adapter.row_2_view_holder) holder;



            final place_data placesData =placeDataList.get(position-2);

            row_2_view_holder.vertical_recycle_view_place_name.setText(placesData.getPlaceName());

            if(placesData.getPlaceImage() != null){

                Glide.with(context)
                        .load(placesData.getPlaceImage())
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .dontAnimate()
                        .placeholder(R.drawable.dummyimage)
                        .into(row_2_view_holder.vertical_recycle_view_place_image);
            }




            row_2_view_holder.place_vertical_parent_recycle_view_row_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    place_parent_recycle_view_click_listener_interface.on_Item_click(position,placesData);
                }
            });
        }
    }

    private void loadRecommenedPlaceList() {

        Query query;
        if(randomPlaceTracker==null)
        {
            query= getINSTANCE().getRootRef().child("Places").limitToFirst(5);


        }
        else {

            query=getINSTANCE().getRootRef().child("Places").orderByChild("placeId").startAt(randomPlaceTracker).limitToFirst(5);

        }


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()){
                    final place_data placeDatavar = data.getValue(place_data.class);

                    getINSTANCE().getRootRef().child("VisitedPlace").child(placeDatavar.getPlaceId()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.hasChild(currentUser))
                            {
                                RecommendedPlaceDataList.add(placeDatavar);
                                randomPlaceTracker=placeDatavar.getPlaceId();
                                place_horizontal_child_recycle_view_adapter.notifyDataSetChanged();



                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });





                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return placeDataList.size()+2;
    }

    class row_0_view_holder extends RecyclerView.ViewHolder {
        AppCompatButton add_new_place_button;
        place_parent_recycle_view_click_listener_interface place_parent_recycle_view_click_listener_interface;

        row_0_view_holder(@NonNull final View itemView ) {
            super(itemView);
            add_new_place_button = itemView.findViewById(R.id.add_new_place_button);
        }


    }

    class row_1_view_holder extends RecyclerView.ViewHolder{

        TextView recommend_place_text_view, tour_place_text_view;
        RecyclerView horizontal_child_recycle_view;

        row_1_view_holder(@NonNull View itemView) {
            super(itemView);
            recommend_place_text_view = itemView.findViewById(R.id.recommended_place_text_view);
            tour_place_text_view = itemView.findViewById(R.id.tour_places_text_view);
            horizontal_child_recycle_view = itemView.findViewById(R.id.place_child_horizontal_recycle_view_id);
        }
    }

    class row_2_view_holder extends RecyclerView.ViewHolder {
        ImageView vertical_recycle_view_place_image;
        TextView vertical_recycle_view_place_name;
        MaterialCardView place_vertical_parent_recycle_view_row_card;
        row_2_view_holder(@NonNull View itemView){
            super(itemView);
            vertical_recycle_view_place_image = itemView.findViewById(R.id.vertical_recyle_view_place_image_id);
            vertical_recycle_view_place_name = itemView.findViewById(R.id.vertical_recycle_view_Place_name_text_view);
            place_vertical_parent_recycle_view_row_card = itemView.findViewById(R.id.place_vertical_parent_recycle_view_row_card_id);
        }
    }
}
