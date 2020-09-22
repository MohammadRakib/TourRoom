package com.example.tourroom.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecyclerviewAdapterForSearch extends RecyclerView.Adapter<RecyclerviewAdapterForSearch.ViewHolder>{

    public interface search_data_get_interface {
        void on_Item_click(int position,place_data placeData);
    }

    List<place_data> placeDataList;
    private Context context;
    search_data_get_interface search_data_get_interface;

    public RecyclerviewAdapterForSearch(List<place_data> placeDataList, Context context,search_data_get_interface search_data_get_interface) {
        this.placeDataList = placeDataList;
        this.context = context;
        this.search_data_get_interface=search_data_get_interface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.row_itemplacesearch,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return  viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


            final place_data placeData = placeDataList.get(position);
            holder.placeText.setText(placeData.getPlaceName());
            if(placeData.getPlaceImage() != null){
                Glide.with(context)
                        .load(placeData.getPlaceImage())
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .dontAnimate()
                        .placeholder(R.drawable.dummyimage)
                        .into(holder.placeImage);


            }
            holder.placeCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    search_data_get_interface.on_Item_click(position,placeData);
                }
            });
        }





    @Override
    public int getItemCount() {
        return placeDataList.size();


    }

    class ViewHolder extends RecyclerView.ViewHolder  {

        ImageView placeImage;
        TextView placeText;
        MaterialCardView placeCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeImage = itemView.findViewById(R.id.rowitemplacesearch_imageviewId);
           placeText = itemView.findViewById(R.id.rowitemplacesearch_textviewid);
           placeCard = itemView.findViewById(R.id.rowitemplacesearch_materiacard);



        }
    }

}
