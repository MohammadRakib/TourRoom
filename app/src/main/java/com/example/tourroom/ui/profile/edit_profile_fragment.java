package com.example.tourroom.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tourroom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class edit_profile_fragment extends Fragment {

    public edit_profile_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_profile_fragment, container, false);
    }
}
