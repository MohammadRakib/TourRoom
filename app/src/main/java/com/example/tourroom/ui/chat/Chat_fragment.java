package com.example.tourroom.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tourroom.R;


public class Chat_fragment extends Fragment {

    RecyclerView chat_recycle_view;
    AppCompatImageButton upload;


    public static Chat_fragment newInstance() {
        return new Chat_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chat_recycle_view = view.findViewById(R.id.show_chat_recycle_view);
        upload = requireActivity().findViewById(R.id.upload_image);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "upload", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
