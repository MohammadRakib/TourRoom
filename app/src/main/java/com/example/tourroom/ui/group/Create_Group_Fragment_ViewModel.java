package com.example.tourroom.ui.group;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Create_Group_Fragment_ViewModel extends ViewModel {

    private MutableLiveData<Uri> image_uri;
    private MutableLiveData<String> group_name;
    private MutableLiveData<String> group_description;

    public Create_Group_Fragment_ViewModel() {
        image_uri = new MutableLiveData<>();
        group_name = new MutableLiveData<>();
        group_description = new MutableLiveData<>();
    }

    public MutableLiveData<Uri> getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(Uri image_uri) {
        this.image_uri.setValue(image_uri);
    }

    public MutableLiveData<String> getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name.setValue(group_name);
    }

    public MutableLiveData<String> getGroup_description() {
        return group_description;
    }

    public void setGroup_description(String group_description) {
        this.group_description.setValue(group_description);
    }
}
