package com.example.tourroom.ui.Poll;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class Poll_fragment extends Fragment {

    private PollFragmentViewModel mViewModel;
    RecyclerView recyclerview_forcreatingpoll;
    RecyclerAdapterForCreatePoll recyclerAdapterForCreatePoll;

    public static Poll_fragment newInstance() {
        return new Poll_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PollFragmentViewModel.class);
        return inflater.inflate(R.layout.poll_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview_forcreatingpoll=view.findViewById(R.id.recyclerviewidforcreatingpoll);
        recyclerAdapterForCreatePoll=new  RecyclerAdapterForCreatePoll();

        recyclerview_forcreatingpoll.setAdapter(recyclerAdapterForCreatePoll);

        final Dialog create_poll_popup_dialog = new Dialog(view.getContext());
        FloatingActionButton actionbutton_forpoll= view.findViewById(R.id.create_pollfloatingbutton);

        actionbutton_forpoll.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                create_poll_popup_dialog.setContentView(R.layout.create_poll_fragment);
                Objects.requireNonNull(create_poll_popup_dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                create_poll_popup_dialog.show();
            }
        });

    }


}
