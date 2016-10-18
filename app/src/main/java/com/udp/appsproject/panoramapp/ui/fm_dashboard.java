package com.udp.appsproject.panoramapp.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.adapter.DashboardAdapter;
import com.udp.appsproject.panoramapp.model.EventsData;
import com.udp.appsproject.panoramapp.model.EventItem;
import java.util.ArrayList;

public class fm_dashboard extends Fragment implements DashboardAdapter.ItemClickCallback{

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    private RecyclerView recView_dashboard;
    private DashboardAdapter adapter_dashboard;
    private ArrayList listData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fm_dashboard, container, false);

        listData = (ArrayList) EventsData.getListData();

        recView_dashboard = (RecyclerView) rootView.findViewById(R.id.recView_events);
        recView_dashboard.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter_dashboard = new DashboardAdapter(EventsData.getListData(), this);
        recView_dashboard.setAdapter(adapter_dashboard);
        adapter_dashboard.setItemClickCallback(this);

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