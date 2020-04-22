package com.example.tourroom.ui.place;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.tourroom.R;
import java.util.Objects;

public class place_parent_fragment extends Fragment implements com.example.tourroom.ui.place.place_vertical_parent_recycle_view_adapter.place_parent_recycle_view_click_listener_interface {

    private NavController navController;
    RecyclerView parent_vertical_recycle_view;
    place_vertical_parent_recycle_view_adapter place_vertical_parent_recycle_view_adapter;

    public static place_parent_fragment newInstance() {
        return new place_parent_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PlaceFragmentViewModel mViewModel = new ViewModelProvider(this).get(PlaceFragmentViewModel.class);
        return inflater.inflate(R.layout.place_fragment, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).isShowing()){
            Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        }

        parent_vertical_recycle_view = view.findViewById(R.id.place_vertical_parent_Recycle_view);
        place_vertical_parent_recycle_view_adapter = new place_vertical_parent_recycle_view_adapter(this);
        parent_vertical_recycle_view.setAdapter(place_vertical_parent_recycle_view_adapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void on_Item_click(int position) {
        navController = Navigation.findNavController(requireActivity(),R.id.after_login_host_fragment);
        navController.navigate(R.id.place_info_fragment);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void on_add_button_click() {
        navController = Navigation.findNavController(requireActivity(),R.id.after_login_host_fragment);
        navController.navigate(R.id.add_new_place_fragment);
    }
}
