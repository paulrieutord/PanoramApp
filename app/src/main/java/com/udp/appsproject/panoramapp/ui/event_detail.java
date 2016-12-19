package com.udp.appsproject.panoramapp.ui;

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
    private static final String EXTRA_TITLE = "EXTRA_TITLE";

    FloatingActionButton fab;
    TextView assistants;
    TextView place;
    TextView website;
    TextView description;
    Boolean childExists;

    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    Bundle extras;

    private FirebaseDatabase FBDatabase;
    private DatabaseReference FBReferenceEvent;
    private DatabaseReference FBReferenceUser;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_detail_event);
        assistants = (TextView) findViewById(R.id.assistants_value);
        place = (TextView) findViewById(R.id.place_value);
        website = (TextView) findViewById(R.id.website_value);
        description = (TextView) findViewById(R.id.description_value);

        user = FirebaseAuth.getInstance().getCurrentUser();

        extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);

        FBDatabase = FirebaseDatabase.getInstance();
        FBReferenceEvent = FBDatabase.getReference("events");
        FBReferenceUser = FBDatabase.getReference("users");

        fab = (FloatingActionButton) findViewById(R.id.fab_detail_event);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (childExists) {
                    FBReferenceEvent.child(extras.getString(EXTRA_KEY)).child("users").child(user.getUid()).getRef().removeValue();
                    FBReferenceUser.child(user.getUid()).child("events").child(extras.getString(EXTRA_KEY)).getRef().removeValue();
                    fab.setImageResource(R.drawable.ic_menu_add);
                    fab.setBackgroundTintList(getResources().getColorStateList(R.color.dark_gray));
                    childExists = false;
                } else {
                    FBReferenceEvent.child(extras.getString(EXTRA_KEY)).child("users").child(user.getUid()).getRef().setValue(true);
                    FBReferenceUser.child(user.getUid()).child("events").child(extras.getString(EXTRA_KEY)).getRef().setValue(extras.getString(EXTRA_TITLE));
                    fab.setImageResource(R.drawable.ic_check_black_24px);
                    fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                    childExists = true;
                }

                FBReferenceEvent.child(extras.getString(EXTRA_KEY)).child("users").addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                assistants.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
            }
        });

        FBReferenceEvent.child(extras.getString(EXTRA_KEY)).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        Event eventObject = dataSnapshot.getValue(Event.class);
                        collapsingToolbarLayout.setTitle(eventObject.getTitle());

                        place.setText(eventObject.getPlace());
                        website.setText(eventObject.getWebsite());
                        description.setText(eventObject.getComment());

                        if (dataSnapshot.child("users").hasChild(user.getUid())) {
                            fab.setImageResource(R.drawable.ic_check_black_24px);
                            fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                            childExists = true;
                        } else {
                            fab.setImageResource(R.drawable.ic_menu_add);
                            fab.setBackgroundTintList(getResources().getColorStateList(R.color.dark_gray));
                            childExists = false;
                        }

                        assistants.setText(String.valueOf(dataSnapshot.child("users").getChildrenCount()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}
