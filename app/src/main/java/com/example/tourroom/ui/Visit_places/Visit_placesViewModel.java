package com.example.tourroom.ui.Visit_places;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Visit_placesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Visit_placesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}