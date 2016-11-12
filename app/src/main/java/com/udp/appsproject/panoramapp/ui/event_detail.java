package com.udp.appsproject.panoramapp.ui;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.model.Event;

public class event_detail extends AppCompatActivity {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_KEY = "EXTRA_KEY";

    FloatingActionButton fab;
    TextView assistants;
    TextView place;
    TextView description;

    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    Bundle extras;

    private DatabaseReference mDatabase;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_detail_event);
        assistants = (TextView) findViewById(R.id.assistants_value);
        place = (TextView) findViewById(R.id.place_value);
        description = (TextView) findViewById(R.id.description_value);

        user = FirebaseAuth.getInstance().getCurrentUser();

        extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        fab = (FloatingActionButton) findViewById(R.id.fab_detail_event);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("events").child(extras.getString(EXTRA_KEY)).addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Get user value
                                Event eventObject = dataSnapshot.getValue(Event.class);

                                if (dataSnapshot.child("users").hasChild(user.getUid())) {
                                    dataSnapshot.child("users").child(user.getUid()).getRef().removeValue();
                                    fab.setImageResource(R.drawable.ic_menu_add);
                                    fab.setBackgroundTintList(getResources().getColorStateList(R.color.dark_gray));
                                } else {
                                    dataSnapshot.child("users").child(user.getUid()).getRef().setValue(true);
                                    fab.setImageResource(R.drawable.ic_check_black_24px);
                                    fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                                }

                                assistants.setText(String.valueOf(dataSnapshot.child("users").getChildrenCount()));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
            }
        });

        mDatabase.child("events").child(extras.getString(EXTRA_KEY)).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Event eventObject = dataSnapshot.getValue(Event.class);
                        collapsingToolbarLayout.setTitle(eventObject.getTitle());

                        place.setText(eventObject.getPlace());
                        description.setText(eventObject.getComment());

                        if (dataSnapshot.child("users").hasChild(user.getUid())) {
                            fab.setImageResource(R.drawable.ic_check_black_24px);
                            fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                        } else {
                            fab.setImageResource(R.drawable.ic_menu_add);
                            fab.setBackgroundTintList(getResources().getColorStateList(R.color.dark_gray));
                        }

                        assistants.setText(String.valueOf(dataSnapshot.child("users").getChildrenCount()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}
