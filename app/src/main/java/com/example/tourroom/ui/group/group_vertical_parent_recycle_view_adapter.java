package com.example.tourroom.ui.group;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.example.tourroom.Data.group_data;
import com.example.tourroom.Data.yourGroupData;
import com.example.tourroom.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;


public class group_vertical_parent_recycle_view_adapter extends RecyclerView.Adapter implements HRecyclerViewClickInterface {

public VRecyclerViewClickInterface vRecyclerViewClickInterface;
    private Context context;
    private List<yourGroupData> groupDataList;
    private final Calendar c = Calendar.getInstance(Locale.getDefault());
    private String date_time;
    String newMessageNumber;

    List<group_data> RecommendedGroupDataList;
    RecyclerAdapterForHorizontalScroll recyclerAdapterForHorizontalScroll;

    public group_vertical_parent_recycle_view_adapter(Context context, VRecyclerViewClickInterface vRecyclerViewClickInterface, List<yourGroupData> groupDataList) {
        this.context = context;
        this.vRecyclerViewClickInterface = vRecyclerViewClickInterface;
        this.groupDataList = groupDataList;
        RecommendedGroupDataList = new ArrayList<>();
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;
        if(viewType == 0){
            view = layoutInflater.inflate(R.layout.group_vertical_parent_recycle_view_row_0,parent,false);
            return new row_0_view_holder(view);
        }else if(viewType == 1){
            view = layoutInflater.inflate(R.layout.group_vertical_parent_recycle_view_row_1,parent,false);
            return new row_1_view_holder(view);
        }
        view = layoutInflater.inflate(R.layout.group_vertical_parent_recycle_view_row_2,parent,false);
        return new row_2_view_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) { //child add
        if (position == 1){

            recyclerAdapterForHorizontalScroll = new RecyclerAdapterForHorizontalScroll(context,this, RecommendedGroupDataList);
            row_1_view_holder row_1_view_holder = (group_vertical_parent_recycle_view_adapter.row_1_view_holder) holder;
            row_1_view_holder.horizontal_child_recycle_view.setAdapter(recyclerAdapterForHorizontalScroll);

            loadGroup();

        }else if(position > 1){
            final row_2_view_holder row_2_view_holder = (group_vertical_parent_recycle_view_adapter.row_2_view_holder) holder;

            final yourGroupData groupData = groupDataList.get(position - 2);

            row_2_view_holder.vertical_recycle_view_group_name.setText(groupData.getGroupName());

            if(groupData.getLastMessage() != null){

                String UserName = groupData.getLastmsgUserName();
                String message =  groupData.getLastMessage();
                String time = groupData.getLastmsgTime();

                long tm = Long.parseLong(time);

                String concatMessage = UserName+": "+message;
                row_2_view_holder.last_message.setText(concatMessage);

                c.setTimeInMillis(tm*1000);
                date_time =  DateFormat.format("MMM d yyyy, h:mm a",c).toString();
                row_2_view_holder.time.setText(date_time);


            }else {
                row_2_view_holder.last_message.setText("");
                row_2_view_holder.time.setText("");
            }

            //loading message count

            if( groupData.getMsgCount() != null && groupData.getMsgCountUser() != null){

                String messageCount = groupData.getMsgCount();
                String messageCountUser = groupData.getMsgCountUser();


                long tempMessageCount = Long.parseLong(messageCount);
                long tempMessageCountUser = Long.parseLong(messageCountUser);
                long tempNewMessageNumber = tempMessageCount - tempMessageCountUser;
                newMessageNumber = String.valueOf(tempNewMessageNumber);


                if(tempNewMessageNumber > 0){

                    row_2_view_holder.last_message.setTypeface(null, Typeface.BOLD);
                    row_2_view_holder.time.setTypeface(null, Typeface.BOLD);

                    row_2_view_holder.messageCounter.setVisibility(View.VISIBLE);
                    if( tempNewMessageNumber < 100 ){
                        row_2_view_holder.messageCounter.setText(newMessageNumber);
                    }else {
                        row_2_view_holder.messageCounter.setText(R.string.above99);
                    }

                }else {
                    row_2_view_holder.messageCounter.setVisibility(View.INVISIBLE);
                    row_2_view_holder.last_message.setTypeface(null, Typeface.NORMAL);
                    row_2_view_holder.time.setTypeface(null, Typeface.NORMAL);
                }

            }

            ///loading image

            if(groupData.getGroupImage() != null){

                Glide.with(context)
                        .load(groupData.getGroupImage())
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .dontAnimate()
                        .placeholder(R.drawable.dummyimage)
                        .into(row_2_view_holder.vertical_recycle_view_group_image);
            }


            row_2_view_holder.group_vertical_parent_recycle_view_row_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vRecyclerViewClickInterface.onItemClickV(position-2, row_2_view_holder.vertical_recycle_view_group_image, row_2_view_holder.vertical_recycle_view_group_name);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return groupDataList.size()+2;
    }

    @Override
    public void onItemClickH(group_data group_data) {
        vRecyclerViewClickInterface.groupinfoonclick(group_data);
    }

    class row_0_view_holder extends RecyclerView.ViewHolder{

        AppCompatButton foraddingnewgroup;

        public row_0_view_holder(@NonNull View itemView) {
            super(itemView);
            foraddingnewgroup = itemView.findViewById(R.id.addgroupbutton);

            foraddingnewgroup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               vRecyclerViewClickInterface.creategrouponclick();
           }
       });

        }
    }

    class row_1_view_holder extends RecyclerView.ViewHolder{

        TextView recommend_group_text_view, tour_group_text_view;
        RecyclerView horizontal_child_recycle_view;

        public row_1_view_holder(@NonNull View itemView) {
            super(itemView);
            recommend_group_text_view = itemView.findViewById(R.id.recommendedgrouptextview);
            tour_group_text_view = itemView.findViewById(R.id.yourgrouptextview);
            horizontal_child_recycle_view = itemView.findViewById(R.id.group_child_horizontal_recycle_view_id);
        }
    }

    class row_2_view_holder extends RecyclerView.ViewHolder{

        CircleImageView vertical_recycle_view_group_image;
        TextView vertical_recycle_view_group_name, last_message, time, messageCounter;
        MaterialCardView group_vertical_parent_recycle_view_row_card;



        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public row_2_view_holder(@NonNull View itemView) {
            super(itemView);
            vertical_recycle_view_group_image = itemView.findViewById(R.id.owngroupimageview);
            vertical_recycle_view_group_name = itemView.findViewById(R.id.owngroupnametextview);
            group_vertical_parent_recycle_view_row_card = itemView.findViewById(R.id.group_vertical_parent_recycle_view_row_card_id);
            vertical_recycle_view_group_image.setTransitionName("gimg"+getAdapterPosition());
            vertical_recycle_view_group_name.setTransitionName("gnm"+getAdapterPosition());

            last_message = itemView.findViewById(R.id.last_group_message);
            messageCounter = itemView.findViewById(R.id.messageCounter);
            time = itemView.findViewById(R.id.time_messege);
        }
    }

    private void loadGroup() {

        getINSTANCE().getRootRef().child("GROUP").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                group_data group_data = snapshot.getValue(group_data.class);
                RecommendedGroupDataList.add(group_data);
                recyclerAdapterForHorizontalScroll.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}



