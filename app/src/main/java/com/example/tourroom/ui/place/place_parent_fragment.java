package com.example.tourroom.ui.place;

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

import com.example.tourroom.Data.place_data;
import com.example.tourroom.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.tourroom.singleton.firebase_init_singleton.getINSTANCE;
import static com.example.tourroom.singleton.yourGroupSingleton.getYourGroupListInstance;
import static com.example.tourroom.singleton.yourPlaceSingleton.getYourPlaceListInstance;

public class place_parent_fragment extends Fragment implements com.example.tourroom.ui.place.place_vertical_parent_recycle_view_adapter.place_parent_recycle_view_click_listener_interface {

    private NavController navController;
    RecyclerView parent_vertical_recycle_view;
    place_vertical_parent_recycle_view_adapter place_vertical_parent_recycle_view_adapter;
    List<place_data> placeData;

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

        placeData=new ArrayList<>();
        parent_vertical_recycle_view = view.findViewById(R.id.place_vertical_parent_Recycle_view);
        place_vertical_parent_recycle_view_adapter=new  place_vertical_parent_recycle_view_adapter(getContext(),this,placeData);
        parent_vertical_recycle_view.setAdapter(place_vertical_parent_recycle_view_adapter);
        navController = Navigation.findNavController(requireActivity(),R.id.after_login_host_fragment);
        loadallPlaceListFromDatabase();
    }

    private void loadallPlaceListFromDatabase() {
        getINSTANCE().getRootRef().child("Places").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    final place_data placeDatas = data.getValue(place_data.class);
                    placeData.add(placeDatas);
                    place_vertical_parent_recycle_view_adapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void on_Item_click(int position,place_data placeData) {
        Intent intent=new Intent(getActivity(),PlaceInfoActivity.class);
        intent.putExtra("id",placeData.getPlaceId());
        startActivity(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void on_add_button_click() {
        navController = Navigation.findNavController(requireActivity(),R.id.after_login_host_fragment);
        navController.navigate(R.id.add_new_place_fragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadallPlaceListFromDatabase();
    }
}
