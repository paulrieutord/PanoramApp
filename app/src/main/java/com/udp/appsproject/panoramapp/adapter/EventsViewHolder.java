package com.udp.appsproject.panoramapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.model.Event;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class EventsViewHolder extends RecyclerView.ViewHolder{
    View mView;
    Context mContext;

    public EventsViewHolder(View itemView) {
        super(itemView);

        mView = itemView;
        mContext = itemView.getContext();
    }

    public void bindEvent (Event events) {
        ImageView icon = (ImageView) itemView.findViewById(R.id.icon_event_item);
        TextView titleEvent = (TextView) itemView.findViewById(R.id.title_event_item);
        TextView placeEvent = (TextView) itemView.findViewById(R.id.place_event_item);
        TextView dateEvent = (TextView) itemView.findViewById(R.id.time_event_item);

        icon.setImageResource(R.drawable.ic_menu_events);
        titleEvent.setText(events.getTitle());
        placeEvent.setText(events.getPlace());
        dateEvent.setText(new SimpleDateFormat("HH:mm", Locale.US).format(events.getDateTime()));
    }
}
