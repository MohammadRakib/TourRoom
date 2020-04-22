package com.example.tourroom.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tourroom.R;
import com.example.tourroom.ui.announcement.RecyclerAdapterForAnnouncement;

public class Chat_fragment extends Fragment {

    RecyclerView chat_recycle_view;
    RecyclerAdapterForAnnouncement recyclerAdapterForAnnouncement;

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
        recyclerAdapterForAnnouncement = new RecyclerAdapterForAnnouncement();
        chat_recycle_view.setAdapter(recyclerAdapterForAnnouncement);
    }
}
