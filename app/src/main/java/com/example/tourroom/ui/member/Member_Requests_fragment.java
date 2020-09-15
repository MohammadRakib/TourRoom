package com.example.tourroom.ui.member;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tourroom.R;

public class Member_Requests_fragment extends Fragment {

    private MemberRequestsFragmentViewModel mViewModel;
    private View view;
    @Nullable
    private Bundle savedInstanceState;
    RecyclerView memberrequestRecycleView;
   memeberRequestAdapter memberrequestAdapter;

    public static Member_Requests_fragment newInstance() {
        return new Member_Requests_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.member__requests_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MemberRequestsFragmentViewModel.class);
        // TODO: Use the ViewModel
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        memberrequestRecycleView = view.findViewById(R.id.memberrequest_recycleview);
        memberrequestAdapter = new memeberRequestAdapter();
        memberrequestRecycleView.setAdapter(memberrequestAdapter);
    }

}
