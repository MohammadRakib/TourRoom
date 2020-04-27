package com.example.tourroom.ui.date_time_picker;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tourroom.ui.event.create_event_view_model;

import java.util.Calendar;

import static com.example.tourroom.ui.event.create_event_activity.create_event_view_model_ob;

public class time_picker_dialog_fragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener{

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour =1 ,minute = 1;
        if (create_event_view_model_ob != null){
            if(create_event_view_model_ob.getHour().getValue() == null && create_event_view_model_ob.getMinute().getValue() == null){
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
            }else {
                hour = create_event_view_model_ob.getHour().getValue();
                minute = create_event_view_model_ob.getMinute().getValue();
            }
        }

        return new TimePickerDialog(requireActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        create_event_view_model_ob.setHour(hourOfDay);
        create_event_view_model_ob.setMinute(minute);
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        if (!(view.is24HourView())){
              create_event_view_model_ob.setAm_pm(calendar.get(Calendar.AM_PM));
          }
        create_event_view_model_ob.setTime_format(DateFormat.format("hh:mm a",calendar).toString());
    }
}
