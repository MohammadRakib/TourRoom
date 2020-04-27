package com.example.tourroom.ui.date_time_picker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import static com.example.tourroom.ui.event.create_event_activity.create_event_view_model_ob;

public class date_picker_dialog_fragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int year = 1,month = 1,day = 1;


        final Calendar c = Calendar.getInstance();
        if(create_event_view_model_ob != null){
            if (create_event_view_model_ob.getYear().getValue() == null && create_event_view_model_ob.getMonth().getValue() == null && create_event_view_model_ob.getDay().getValue() == null){
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

            }else {
                year = create_event_view_model_ob.getYear().getValue();
                month = create_event_view_model_ob.getMonth().getValue();
                day = create_event_view_model_ob.getDay().getValue();
            }
        }

        return new DatePickerDialog(requireActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        create_event_view_model_ob.setYear(year);
        create_event_view_model_ob.setMonth(month);
        create_event_view_model_ob.setDay(dayOfMonth);
        create_event_view_model_ob.setDate_format(DateFormat.format("EEEE, MMM d, yyyy",calendar).toString());
    }
}
