package com.udp.appsproject.panoramapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.adapter.DashboardAdapter;
import com.udp.appsproject.panoramapp.adapter.EventsAdapter;
import com.udp.appsproject.panoramapp.model.EventsData;
import com.udp.appsproject.panoramapp.model.EventItem;
import java.util.ArrayList;

public class tabFm_events_popular extends Fragment implements EventsAdapter.ItemClickCallback{

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    private RecyclerView recView_events;
    private EventsAdapter adapter_events;
    private ArrayList listData;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static tabFm_events_popular newInstance(int sectionNumber) {
        tabFm_events_popular fragment = new tabFm_events_popular();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public tabFm_events_popular() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_fm_events_popular, container, false);

        listData = (ArrayList) EventsData.getListData();

        recView_events = (RecyclerView) rootView.findViewById(R.id.recView_events);
        recView_events.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter_events = new EventsAdapter(EventsData.getListData(), this.getContext());
        recView_events.setAdapter(adapter_events);
        adapter_events.setItemClickCallback(this);

        return rootView;
    }

    @Override
    public void onItemClick(int p) {
        EventItem item = (EventItem) listData.get(p);

        Intent i = new Intent(getActivity(), event_detail.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_QUOTE, item.getTitleEvent());
        extras.putString(EXTRA_ATTR, item.getPlace());
        i.putExtra(BUNDLE_EXTRAS, extras);

        startActivity(i);
    }
}