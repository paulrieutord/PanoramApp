package com.udp.appsproject.panoramapp.ui;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.model.Event;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class event_detail extends AppCompatActivity {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_KEY = "EXTRA_KEY";
    private static final String EXTRA_TITLE = "EXTRA_TITLE";

    FloatingActionButton fab;
    TextView assistants;
    TextView place;
    TextView datetime;
    TextView website;
    TextView description;
    ImageView photo;
    public long countUsers;
    Boolean childExists;

    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    Bundle extras;

    private FirebaseDatabase FBDatabase;
    private DatabaseReference FBReferenceEvent;
    private DatabaseReference FBReferenceUser;
    private StorageReference imageReference;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_detail_event);
        assistants = (TextView) findViewById(R.id.assistants_value);
        place = (TextView) findViewById(R.id.place_value);
        datetime = (TextView) findViewById(R.id.datetime_value);
        website = (TextView) findViewById(R.id.website_value);
        description = (TextView) findViewById(R.id.description_value);
        photo = (ImageView) findViewById(R.id.photo_detail);

        imageReference = FirebaseStorage.getInstance().getReference();

        extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);

        imageReference.child("Photos/"+extras.getString(EXTRA_KEY)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso
                        .with(getApplicationContext())
                        .load(uri)
                        .centerCrop()
                        .fit()
                        .into(photo);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();

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
                                countUsers = dataSnapshot.getChildrenCount();
                                assistants.setText(String.valueOf(dataSnapshot.getChildrenCount()));
                                FBReferenceEvent.child(extras.getString(EXTRA_KEY)).child("countUsers").setValue(countUsers);
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
                        datetime.setText(new SimpleDateFormat("dd/MM/yyy", Locale.US).format(eventObject.getCreatedAt())+" a las "
                                +new SimpleDateFormat("HH:mm", Locale.US).format(eventObject.getCreatedAt()));
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
