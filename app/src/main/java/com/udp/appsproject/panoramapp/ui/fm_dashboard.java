package com.udp.appsproject.panoramapp.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.adapter.DashboardViewHolder;
import com.udp.appsproject.panoramapp.model.Event;

public class fm_dashboard extends Fragment {

    private RecyclerView recView_dashboard;
    public FirebaseRecyclerAdapter adapter_dashboard;

    private FirebaseDatabase FBDatabase;
    private DatabaseReference FBReference;
    private Query queryRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fm_dashboard, container, false);

        recView_dashboard = (RecyclerView) rootView.findViewById(R.id.recView_events_popular);

        FBDatabase = FirebaseDatabase.getInstance();
        FBReference = FBDatabase.getReference("events");
        queryRef = FBReference.orderByChild("dateTime");

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter_dashboard = new FirebaseRecyclerAdapter<Event, DashboardViewHolder>(
                Event.class,
                R.layout.event_cardview_dashboard,
                DashboardViewHolder.class,
                FBReference
        ) {
            @Override
            protected void populateViewHolder(DashboardViewHolder viewHolder, Event model, int position) {
                viewHolder.bindEvents(model, adapter_dashboard.getRef(position).getKey());
            }
        };

        LinearLayoutManager layoutM = new LinearLayoutManager(getActivity());
        layoutM.setReverseLayout(true);
        layoutM.setStackFromEnd(true);
        recView_dashboard.setHasFixedSize(true);
        recView_dashboard.setLayoutManager(layoutM);
        recView_dashboard.setAdapter(adapter_dashboard);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter_dashboard.cleanup();
    }
}