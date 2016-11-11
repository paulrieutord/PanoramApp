package com.udp.appsproject.panoramapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.model.Event;
import com.udp.appsproject.panoramapp.ui.event_detail;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_KEY = "EXTRA_KEY";

    View mView;
    Context mContext;
    String mKey;

    public EventsViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindEvent (Event events, String key) {
        mKey = key;
        ImageView icon = (ImageView) itemView.findViewById(R.id.icon_event_item);
        TextView titleEvent = (TextView) itemView.findViewById(R.id.title_event_item);
        TextView placeEvent = (TextView) itemView.findViewById(R.id.place_event_item);
        TextView dateEvent = (TextView) itemView.findViewById(R.id.time_event_item);
        mView.setOnClickListener(this);

        icon.setImageResource(R.drawable.ic_menu_events);
        titleEvent.setText(events.getTitle());
        placeEvent.setText(events.getPlace());
        dateEvent.setText(new SimpleDateFormat("HH:mm", Locale.US).format(events.getDateTime()));
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
