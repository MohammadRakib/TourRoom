package com.example.tourroom.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Integer> pending_notification;

    public HomeViewModel() {
        pending_notification=new MutableLiveData<>();
        pending_notification.setValue(3);
    }

    LiveData<Integer> get_pending_notification(){return pending_notification;}
}
