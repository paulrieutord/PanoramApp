package com.udp.appsproject.panoramapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udp.appsproject.panoramapp.R;

public class tabFm_events_popular extends Fragment {

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

        return rootView;
    }
}