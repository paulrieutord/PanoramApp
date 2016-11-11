package com.udp.appsproject.panoramapp.ui;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.model.Event;
import com.udp.appsproject.panoramapp.model.User;

public class event_detail extends AppCompatActivity {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_KEY = "EXTRA_KEY";

    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_detail_event);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);

        mDatabase.child("events").child(extras.getString(EXTRA_KEY)).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Event eventObject = dataSnapshot.getValue(Event.class);
                        collapsingToolbarLayout.setTitle(eventObject.getTitle());

                        ((TextView)findViewById(R.id.place_value)).setText(eventObject.getPlace());
                        ((TextView)findViewById(R.id.description_value)).setText(eventObject.getComment());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}
