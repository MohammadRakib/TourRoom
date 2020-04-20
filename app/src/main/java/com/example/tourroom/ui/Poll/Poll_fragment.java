package com.example.tourroom.ui.Poll;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tourroom.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class Poll_fragment extends Fragment {

    private PollFragmentViewModel mViewModel;
    RecyclerView recyclerview_forcreatingpoll;
    RecyclerAdapterForCreatePoll recyclerAdapterForCreatePoll;
    FloatingActionButton actionbutton_forpoll;

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
        actionbutton_forpoll= view.findViewById(R.id.create_pollfloatingbutton);

        recyclerAdapterForCreatePoll=new  RecyclerAdapterForCreatePoll();

        recyclerview_forcreatingpoll.setAdapter(recyclerAdapterForCreatePoll);
        recyclerview_forcreatingpoll.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0) {
                    actionbutton_forpoll.show();

                } else if (dy > 0) {
                    actionbutton_forpoll.hide();
                }
            }
        });



        actionbutton_forpoll.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), create_poll_activity.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

    }

}
