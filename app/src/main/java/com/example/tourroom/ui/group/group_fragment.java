package com.example.tourroom.ui.group;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tourroom.R;

import java.util.Objects;

public class group_fragment extends Fragment  implements  VRecyclerViewClickInterface {

    private GroupFragmentViewModel mViewModel;
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
        mViewModel = new ViewModelProvider(this).get(GroupFragmentViewModel.class);
        // TODO: Use the ViewModel
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).isShowing()){
            Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
        }

        verticalparent_recyclerView=view.findViewById(R.id.group_vertical_parent_Recycle_view);
        group_vertical_parent_recycle_view_adapterVariable = new group_vertical_parent_recycle_view_adapter(this);
        verticalparent_recyclerView.setAdapter(group_vertical_parent_recycle_view_adapterVariable);
        navController = Navigation.findNavController(Objects.requireNonNull(getActivity()),R.id.after_login_host_fragment);

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onItemClickV(int position) {
        Intent intent = new Intent(getActivity(),group_host_activity.class);
        startActivity(intent);
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
