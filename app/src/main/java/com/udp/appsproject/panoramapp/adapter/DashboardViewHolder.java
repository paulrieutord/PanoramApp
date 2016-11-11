package com.udp.appsproject.panoramapp.adapter;

import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DashboardViewHolder extends RecyclerView.ViewHolder {

    View mView;
    Context mContext;

    private DatabaseReference mDatabase;

    public DashboardViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mContext = itemView.getContext();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void bindEvents (Event events) {
        ImageView avatar = (ImageView) itemView.findViewById(R.id.avatar_dashboard_item);
        ImageView photo = (ImageView) itemView.findViewById(R.id.photo_dashboard_item);
        final TextView name = (TextView) itemView.findViewById(R.id.name_dashboard_item);
        TextView titleEvent = (TextView) itemView.findViewById(R.id.titleEvent_dashboard_item);
        TextView action = (TextView) itemView.findViewById(R.id.action_dashboard_item);
        TextView timeAction = (TextView) itemView.findViewById(R.id.timeAction_dashboard_item);
        TextView dateEvent = (TextView) itemView.findViewById(R.id.dateEvent_dashboard_item);
        TextView place = (TextView) itemView.findViewById(R.id.place_dashboard_item);

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
        titleEvent.setText(events.getTitle());
        action.setText("Asistirá");
        timeAction.setText(new SimpleDateFormat("HH:mm", Locale.US).format(events.getCreatedAt()));
        dateEvent.setText(new SimpleDateFormat("dd-MM-yyy", Locale.US).format(events.getDateTime()));
        place.setText(events.getPlace());
    }
}
