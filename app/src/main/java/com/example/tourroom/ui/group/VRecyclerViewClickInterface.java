package com.example.tourroom.ui.group;

import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public interface VRecyclerViewClickInterface {

    void onItemClickV(int position, CircleImageView group_img, TextView group_name);
    void creategrouponclick();
    void groupinfoonclick(int position);
}
