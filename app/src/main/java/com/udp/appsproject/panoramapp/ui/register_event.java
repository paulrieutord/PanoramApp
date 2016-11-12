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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class register_event extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText eventTitle;
    TextView eventDate;
    TextView eventTime;
    EditText eventPlace;
    EditText eventDescription;
    Spinner categorieSpinner;
    List<String> categoriesList;
    Button eventOk;

    int day_x, month_x, year_x, hour_x, minute_x;
    int finalDay, finalMonth, finalYear, finalHour, finalMinute;

    private DatabaseReference FBDatabase;
    private FirebaseDatabase FBInstance;

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

        categorieSpinner = (Spinner) findViewById(R.id.spinner_categories);

        FBInstance = FirebaseDatabase.getInstance();

        FBDatabase = FBInstance.getReference("eventCategories");

        FBDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoriesList = new ArrayList<>();

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {
                    String categorie = areaSnapshot.getValue(String.class);

                    categoriesList.add(categorie);
                }

                ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categoriesList);
                categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorieSpinner.setAdapter(categoriesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
                DPDialog.setTitle("");
                DPDialog.show();
                break;
            case R.id.register_event_time:
                newCalendar = Calendar.getInstance();

                hour_x = newCalendar.get(Calendar.HOUR_OF_DAY);
                minute_x = newCalendar.get(Calendar.MINUTE);

                TimePickerDialog TPDialog = new TimePickerDialog(register_event.this, register_event.this, hour_x, minute_x, true);
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

        long stampView = componentTimeToTimestamp(finalYear, finalMonth, finalDay, 0, 0);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy", Locale.US);
        String s = sdf.format(stampView);

        eventDate.setText(s);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        finalHour = hourOfDay;
        finalMinute = minute;

        long stampView = componentTimeToTimestamp(0, 0, 0, finalHour, finalMinute);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.US);
        String s = sdf.format(stampView);

        eventTime.setText(s);
    }

    public void createEvent() {
        //Reset errors
        eventTitle.setError(null);
        eventPlace.setError(null);
        eventDescription.setError(null);

        String title = eventTitle.getText().toString().trim();
        String place = eventPlace.getText().toString().trim();
        String description = eventDescription.getText().toString().trim();
        String categorie = categorieSpinner.getSelectedItem().toString();

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

        if (TextUtils.isEmpty(categorie)) {
            Toast.makeText(this, "Debe elegir una categoría", Toast.LENGTH_SHORT).show();
            focusView = categorieSpinner;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            FBDatabase = FBInstance.getReference("events");
            String keyEvent = FBDatabase.push().getKey();

            long longDateTime = componentTimeToTimestamp(finalYear, finalMonth, finalDay, finalHour, finalMinute);
            long longCreatedAt = System.currentTimeMillis();

            String createdBy;

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                createdBy = user.getUid();
            } else {
                createdBy = "";
            }

            Event eventDB = new Event(title, longDateTime, longCreatedAt, place, description, createdBy, categorie);

            FBDatabase.child(keyEvent).setValue(eventDB);

            addUserChangeListener(keyEvent);
        }
    }

    long componentTimeToTimestamp(int year, int month, int day, int hour, int minute) {

        Calendar c = new GregorianCalendar(year, month - 1, day, hour, minute, 0);

        return c.getTimeInMillis();
    }

    private void addUserChangeListener(String keyEvent) {
        // User data change listener
        FBDatabase.child(keyEvent).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Event event = dataSnapshot.getValue(Event.class);

                // Check for null
                if (event == null) {
                    return;
                }

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