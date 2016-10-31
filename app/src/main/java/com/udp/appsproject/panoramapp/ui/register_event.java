package com.udp.appsproject.panoramapp.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import com.udp.appsproject.panoramapp.R;
import java.util.Calendar;

public class register_event extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText eventTitle;
    TextView eventDate;
    TextView eventTime;
    EditText eventPlace;
    EditText eventDescription;

    int day_x, month_x, year_x, hour_x, minute_x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_register_event);
        setSupportActionBar(toolbar);

        eventTitle = (EditText) findViewById(R.id.register_event_title);
        eventDate = (TextView) findViewById(R.id.register_event_date);
        eventDate.setOnClickListener(this);
        eventTime = (TextView) findViewById(R.id.register_event_time);
        eventTime.setOnClickListener(this);
        eventPlace = (EditText) findViewById(R.id.register_event_place);
        eventDescription = (EditText) findViewById(R.id.register_event_description);
    }

    @Override
    public void onClick(View v) {
        Calendar newCalendar;
        switch (v.getId()) {
            case R.id.register_event_date:
                newCalendar = Calendar.getInstance();

                day_x = newCalendar.get(Calendar.DAY_OF_MONTH);
                month_x = newCalendar.get(Calendar.MONTH);
                year_x = newCalendar.get(Calendar.YEAR);

                DatePickerDialog DPDialog = new DatePickerDialog(register_event.this, register_event.this, year_x, month_x, day_x);
                DPDialog.setTitle("Seleccione fecha");
                DPDialog.show();
                break;
            case R.id.register_event_time:
                newCalendar = Calendar.getInstance();

                hour_x = newCalendar.get(Calendar.HOUR_OF_DAY);
                minute_x = newCalendar.get(Calendar.MINUTE);

                TimePickerDialog TPDialog = new TimePickerDialog(register_event.this, register_event.this, hour_x, minute_x, true);
                TPDialog.setTitle("Seleccione hora");
                TPDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int finalMonth = month + 1;
        eventDate.setText(dayOfMonth + "/" + finalMonth + "/" + year);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        eventTime.setText(hourOfDay + ":" + minute);
    }
}