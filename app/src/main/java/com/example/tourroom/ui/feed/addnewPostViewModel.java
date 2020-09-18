package com.example.tourroom.ui.feed;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class addnewPostViewModel extends ViewModel{

        private MutableLiveData<Uri> image_uri;

    public addnewPostViewModel() {
        image_uri = new MutableLiveData<>();
    }

    MutableLiveData<Uri> getImage_uri() {
        return image_uri;
    }

    void setImage_uri(Uri image_uri) {
        this.image_uri.setValue(image_uri);
    }

}
