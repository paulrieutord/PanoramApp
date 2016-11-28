package com.udp.appsproject.panoramapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_KEY = "EXTRA_KEY";
    private static final String EXTRA_TITLE = "EXTRA_TITLE";

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
    String mTitleEvent;

    private DatabaseReference mDatabase;

    public DashboardViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mContext = itemView.getContext();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void bindEvents (Event events, String key) {
        mKey = key;
        mTitleEvent = events.getTitle();
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
        action.setText("Creó el evento");

        Calendar calendarAux = Calendar.getInstance();
        calendarAux.setTime(new Date(System.currentTimeMillis()));

        Date now = new Date(calendarAux.getTimeInMillis());

        calendarAux.set(Calendar.HOUR_OF_DAY, 0);
        calendarAux.set(Calendar.MINUTE, 0);
        calendarAux.set(Calendar.SECOND, 0);

        Date startToday = new Date(calendarAux.getTimeInMillis());

        calendarAux.add(Calendar.DATE, -1);
        Date startYesterday = new Date(calendarAux.getTimeInMillis());

        Date createdAt = new Date(events.getCreatedAt());

        if (createdAt.after(startToday) && createdAt.before(now)) {
            String format = new SimpleDateFormat("HH:mm", Locale.US).format(events.getCreatedAt())+" hrs.";
            timeAction.setText(format);
        } else if (createdAt.after(startYesterday) && createdAt.before(startToday)) {
            String format = "Ayer a las "+new SimpleDateFormat("HH:mm", Locale.US).format(events.getCreatedAt())+" hrs.";
            timeAction.setText(format);
        } else {
            timeAction.setText(new SimpleDateFormat("dd-MM-yyy HH:mm", Locale.US).format(events.getCreatedAt()));
        }

        String formatDate = new SimpleDateFormat("dd-MM-yyy", Locale.US).format(events.getDateTime())+" a las "+new SimpleDateFormat("HH:mm", Locale.US).format(events.getDateTime())+" hrs.";
        dateEvent.setText(formatDate);
        place.setText(events.getPlace());
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(mContext, event_detail.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_KEY, mKey);
        extras.putString(EXTRA_TITLE, mTitleEvent);
        i.putExtra(BUNDLE_EXTRAS, extras);

        mContext.startActivity(i);
    }
}
