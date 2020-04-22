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
import com.example.tourroom.R;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;

public class group_fragment extends Fragment  implements  VRecyclerViewClickInterface {

    private NavController navController;
    RecyclerView verticalparent_recyclerView;
    group_vertical_parent_recycle_view_adapter group_vertical_parent_recycle_view_adapterVariable;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.group_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GroupFragmentViewModel mViewModel = new ViewModelProvider(this).get(GroupFragmentViewModel.class);
        // TODO: Use the ViewModel
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
        group_vertical_parent_recycle_view_adapterVariable = new group_vertical_parent_recycle_view_adapter(this);
        verticalparent_recyclerView.setAdapter(group_vertical_parent_recycle_view_adapterVariable);
        navController = Navigation.findNavController(requireActivity(),R.id.after_login_host_fragment);

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
