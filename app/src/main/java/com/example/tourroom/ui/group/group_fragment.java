package com.example.tourroom.ui.group;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourroom.Data.group_data;
import com.example.tourroom.Data.yourGroupData;
import com.example.tourroom.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;

public class group_fragment extends Fragment  implements  VRecyclerViewClickInterface {

    private NavController navController;
    RecyclerView verticalparent_recyclerView;
    group_vertical_parent_recycle_view_adapter group_vertical_parent_recycle_view_adapterVariable;

    private String currentUserID;

    //use for tracking which group user opened
    static int yourGroupIntoPosition = -1;
    static String yourGroupIntoId = null;

    //listener and query for newMessageTracker
    Query newMessageQuery;
    ChildEventListener newMessageListener;

    boolean breaks = false; //for breaking the loop in the listener;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.group_fragment, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Fade fade = new Fade();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fade.excludeTarget(requireActivity().findViewById(R.id.main_toolbar_layout_id), true);
            fade.excludeTarget(requireActivity().findViewById(R.id.bottom_home_nav),true);
            fade.excludeTarget(requireActivity().findViewById(R.id.bottom_nav_host_fragment),true);
            fade.excludeTarget(requireActivity().findViewById(R.id.id_home_fragment),true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            requireActivity().getWindow().setEnterTransition(fade);
            requireActivity().getWindow().setExitTransition(fade);
        }

        if(!Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).isShowing()){
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        }

        verticalparent_recyclerView=view.findViewById(R.id.group_vertical_parent_Recycle_view);
        navController = Navigation.findNavController(requireActivity(),R.id.after_login_host_fragment);

        currentUserID = Objects.requireNonNull(getINSTANCE().getMAuth().getCurrentUser()).getUid();

        //setting up adapter
        group_vertical_parent_recycle_view_adapterVariable = new group_vertical_parent_recycle_view_adapter(requireActivity(),this, getYourGroupListInstance().getYourGroupList());
        verticalparent_recyclerView.setAdapter(group_vertical_parent_recycle_view_adapterVariable);
        //sending to adapter
        group_vertical_parent_recycle_view_adapterVariable.notifyDataSetChanged();

    }


    @Override
    public void onStart() {
        super.onStart();

    }





    @Override
    public void onItemClickV(int position, CircleImageView group_img, TextView group_name) {
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View,String>(group_img,"gimg"+position);
        pairs[1] = new Pair<View,String>(group_name,"gnm"+position);
        Intent intent = new Intent(getActivity(),group_host_activity.class);
        intent.putExtra("position",position);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),pairs);
        startActivity(intent,activityOptionsCompat.toBundle());
    }

    @Override
    public void creategrouponclick() {
        navController.navigate(R.id.create_group_fragment);
    }

    @Override
    public void groupinfoonclick(int position) {
        navController.navigate(R.id.group_info_fragment);
    }
}
