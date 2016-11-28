package com.udp.appsproject.panoramapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.udp.appsproject.panoramapp.adapter.EventsViewHolder;
import com.udp.appsproject.panoramapp.model.Event;

import java.util.Calendar;
import java.util.Date;

public class tabFm_events_today extends Fragment {

    private RecyclerView recView_events;
    private FirebaseRecyclerAdapter adapter_events;

    private FirebaseDatabase FBDatabase;
    private DatabaseReference FBReference;
    private Query queryRef;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static tabFm_events_today newInstance(int sectionNumber) {
        tabFm_events_today fragment = new tabFm_events_today();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public tabFm_events_today() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_fm_events_popular, container, false);

        recView_events = (RecyclerView) rootView.findViewById(R.id.recView_events);

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(new Date(System.currentTimeMillis()));
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(new Date(System.currentTimeMillis()));
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);

        FBDatabase = FirebaseDatabase.getInstance();
        FBReference = FBDatabase.getReference("events");
        queryRef = FBReference.orderByChild("dateTime").startAt(calendarStart.getTimeInMillis()).endAt(calendarEnd.getTimeInMillis());

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter_events = new FirebaseRecyclerAdapter<Event, EventsViewHolder>(
                Event.class,
                R.layout.event_item,
                EventsViewHolder.class,
                queryRef
        ) {
            @Override
            protected void populateViewHolder(EventsViewHolder viewHolder, Event model, int position) {
                viewHolder.bindEvent(model, adapter_events.getRef(position).getKey());
            }
        };

        recView_events.setHasFixedSize(true);
        recView_events.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView_events.setAdapter(adapter_events);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter_events.cleanup();
    }
}