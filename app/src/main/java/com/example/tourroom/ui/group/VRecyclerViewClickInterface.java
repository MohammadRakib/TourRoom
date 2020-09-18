package com.example.tourroom.ui.group;

import android.widget.TextView;

import com.example.tourroom.Data.group_data;

import de.hdodenhof.circleimageview.CircleImageView;

public interface VRecyclerViewClickInterface {

    void onItemClickV(int position);
    void creategrouponclick();
    void groupinfoonclick(group_data group_data);
}
