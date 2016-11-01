package com.udp.appsproject.panoramapp.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.model.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class register_event extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText eventTitle;
    TextView eventDate;
    TextView eventTime;
    EditText eventPlace;
    EditText eventDescription;
    Button eventOk;

    int day_x, month_x, year_x, hour_x, minute_x;
    int finalDay, finalMonth, finalYear, finalHour, finalMinute;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

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
        eventOk = (Button) findViewById(R.id.register_event_ok);
        eventOk.setOnClickListener(this);

        mFirebaseInstance = FirebaseDatabase.getInstance();
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
                DPDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                DPDialog.show();
                break;
            case R.id.register_event_time:
                newCalendar = Calendar.getInstance();

                hour_x = newCalendar.get(Calendar.HOUR_OF_DAY);
                minute_x = newCalendar.get(Calendar.MINUTE);

                TimePickerDialog TPDialog = new TimePickerDialog(register_event.this, register_event.this, hour_x, minute_x, true);
                //TPDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                TPDialog.setTitle("");
                TPDialog.show();
                break;
            case R.id.register_event_ok:
                createEvent();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        finalDay = dayOfMonth;
        finalMonth = month + 1;
        finalYear = year;
        eventDate.setText(finalDay + "/" + finalMonth + "/" + finalYear);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        finalHour = hourOfDay;
        finalMinute = minute;
        eventTime.setText(String.format(Locale.US, "%02d", finalHour) + ":" + String.format(Locale.US, "%02d", finalMinute));
    }

    public void createEvent() {
        //Reset errors
        eventTitle.setError(null);
        eventPlace.setError(null);
        eventDescription.setError(null);

        String title = eventTitle.getText().toString().trim();
        String place = eventPlace.getText().toString().trim();
        String description = eventDescription.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for valid fields

        if (TextUtils.isEmpty(title)) {
            eventTitle.setError(getString(R.string.error_field_required));
            focusView = eventTitle;
            cancel = true;
        }

        if (TextUtils.isEmpty(place)) {
            eventPlace.setError(getString(R.string.error_field_required));
            focusView = eventPlace;
            cancel = true;
        }

        if (TextUtils.isEmpty(description)) {
            eventDescription.setError(getString(R.string.error_field_required));
            focusView = eventDescription;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mFirebaseDatabase = mFirebaseInstance.getReference("events");
            String keyEvent = mFirebaseDatabase.push().getKey();

            long longDateTime = componentTimeToTimestamp(finalYear, finalMonth, finalDay, finalHour, finalMinute);
            long longCreatedAt = System.currentTimeMillis()/1000;
            String createdBy;

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                createdBy = user.getUid();
            } else {
                createdBy = "";
            }

            Event eventDB = new Event(title, longDateTime, longCreatedAt, place, description, createdBy);

            mFirebaseDatabase.child(keyEvent).setValue(eventDB);

            addUserChangeListener(keyEvent);
        }
    }

    long componentTimeToTimestamp(int year, int month, int day, int hour, int minute) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (c.getTimeInMillis() / 1000L);
    }

    private void addUserChangeListener(String keyEvent) {
        // User data change listener
        mFirebaseDatabase.child(keyEvent).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);

                // Check for null
                if (event == null) {
                    Log.e("EVENT_NULL", "User data is null!");
                    return;
                }

                Log.e("EVENT_CHANGED", "Event data is changed!" + event.getTitle() + ", " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(event.getCreatedAt()));

                Toast.makeText(register_event.this, "Evento creado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), main.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("EVENT_FAILED_READ", "Failed to read event", error.toException());
            }
        });
    }
}