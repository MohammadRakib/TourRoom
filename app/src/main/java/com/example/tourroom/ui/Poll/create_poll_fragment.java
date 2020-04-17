package com.example.tourroom.ui.Poll;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tourroom.R;

public class create_poll_fragment extends Fragment {

    private CreatePollFragmentViewModel mViewModel;

    public static create_poll_fragment newInstance() {
        return new create_poll_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_poll_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreatePollFragmentViewModel.class);
        // TODO: Use the ViewModel
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button createnewpollbutton;
        ImageView addpolloption=view.findViewById(R.id.addpolloption_imageview);
        TextView option1textview_forpoll,option2textview_forpoll;
        EditText option1_edittext_forpoll,option2_edittext_forpoll;
        option1textview_forpoll=view.findViewById(R.id.option1textview);
        option2textview_forpoll=view.findViewById(R.id.option2textview);
        option1_edittext_forpoll=view.findViewById(R.id.option1_edittext_forcreatingpoll);
        option2_edittext_forpoll=view.findViewById(R.id.option2_edittext_forcreatingpoll);
        createnewpollbutton=view.findViewById(R.id.create_newpollbutton);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
