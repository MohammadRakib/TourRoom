package com.example.tourroom.ui.event;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class create_event_view_model extends ViewModel {
    private MutableLiveData<Integer> hour;
    private MutableLiveData<Integer> minute;
    private MutableLiveData<Integer> am_pm;
    private MutableLiveData<Integer> month;
    private MutableLiveData<Integer> day;
    private MutableLiveData<Integer> year;
    private MutableLiveData<String> date_format;
    private MutableLiveData<Integer> e_day;
    private MutableLiveData<String> time_format;
    private MutableLiveData<String> event_name;
    private MutableLiveData<Uri> image_uri;

    MutableLiveData<Uri> getImage_uri() {
        return image_uri;
    }

    public create_event_view_model() {
        hour = new MutableLiveData<>();
        minute = new MutableLiveData<>();
        month = new MutableLiveData<>();
        day = new MutableLiveData<>();
        year = new MutableLiveData<>();
        am_pm = new MutableLiveData<>();
        date_format = new MutableLiveData<>();
        e_day = new MutableLiveData<>();
        time_format = new MutableLiveData<>();
        event_name = new MutableLiveData<>();
        image_uri = new MutableLiveData<>();
    }

    MutableLiveData<String> getEvent_name() {
        return event_name;
    }

    public MutableLiveData<Integer> getE_day() {
        return e_day;
    }

    MutableLiveData<String> getTime_format() {
        return time_format;
    }

    MutableLiveData<String> getDate_format() {
        return date_format;
    }

    public MutableLiveData<Integer> getAm_pm() {
        return am_pm;
    }

    public MutableLiveData<Integer> getHour() {
        return hour;
    }

    public MutableLiveData<Integer> getMinute() {
        return minute;
    }


    public MutableLiveData<Integer> getMonth() {
        return month;
    }

    public MutableLiveData<Integer> getDay() {
        return day;
    }


    public MutableLiveData<Integer> getYear() {
        return year;
    }

    public void setHour(int hour) {
        this.hour.setValue(hour);
    }

    public void setMinute(int minute) {
        this.minute.setValue(minute);
    }

    public void setMonth(int month) {
        this.month.setValue(month);
    }

    public void setDay(int day) {
        this.day.setValue(day);
    }

    public void setYear(int year) {
        this.year.setValue(year);
    }

    public void setAm_pm(int am_pm) {
        this.am_pm.setValue(am_pm);
    }

    public void setDate_format(String date_format) {
        this.date_format.setValue(date_format);
    }

    public void setE_day(int e_day) {
        this.e_day.setValue(e_day);
    }

    public void setTime_format(String time_format) {
        this.time_format.setValue(time_format);
    }

    void setEvent_name(String event_name) {
        this.event_name.setValue(event_name);
    }

    void setImage_uri(Uri image_uri) {
        this.image_uri.setValue(image_uri);
    }
}
