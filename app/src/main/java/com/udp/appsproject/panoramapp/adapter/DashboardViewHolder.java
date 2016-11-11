package com.udp.appsproject.panoramapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.model.Event;
import com.udp.appsproject.panoramapp.model.User;
import com.udp.appsproject.panoramapp.ui.event_detail;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DashboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_KEY = "EXTRA_KEY";

    View mView;
    Context mContext;
    ImageView avatar;
    ImageView photo;
    TextView name;
    TextView titleEvent;
    TextView action;
    TextView timeAction;
    TextView dateEvent;
    TextView place;
    String mKey;

    private DatabaseReference mDatabase;

    public DashboardViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mContext = itemView.getContext();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void bindEvents (Event events, String key) {
        mKey = key;
        avatar = (ImageView) itemView.findViewById(R.id.avatar_dashboard_item);
        photo = (ImageView) itemView.findViewById(R.id.photo_dashboard_item);
        name = (TextView) itemView.findViewById(R.id.name_dashboard_item);
        titleEvent = (TextView) itemView.findViewById(R.id.titleEvent_dashboard_item);
        action = (TextView) itemView.findViewById(R.id.action_dashboard_item);
        timeAction = (TextView) itemView.findViewById(R.id.timeAction_dashboard_item);
        dateEvent = (TextView) itemView.findViewById(R.id.dateEvent_dashboard_item);
        place = (TextView) itemView.findViewById(R.id.place_dashboard_item);

        mDatabase.child("users").child(events.getCreatedBy()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User userObject = dataSnapshot.getValue(User.class);
                        name.setText(userObject.getUserName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        avatar.setImageResource(android.R.drawable.sym_def_app_icon);
        photo.setImageResource(R.drawable.ic_menu_events);
        photo.setOnClickListener(this);
        titleEvent.setText(events.getTitle());
        action.setText("Asistirá");
        timeAction.setText(new SimpleDateFormat("HH:mm", Locale.US).format(events.getCreatedAt()));
        dateEvent.setText(new SimpleDateFormat("dd-MM-yyy", Locale.US).format(events.getDateTime()));
        place.setText(events.getPlace());
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(mContext, event_detail.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_KEY, mKey);
        i.putExtra(BUNDLE_EXTRAS, extras);

        mContext.startActivity(i);
    }
}
