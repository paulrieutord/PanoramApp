package com.udp.appsproject.panoramapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

        switch (events.getCategorie()) {
            case "Bar":
                icon.setImageResource(R.drawable.ic_category_bar_24px);
                break;
            case "Café":
                icon.setImageResource(R.drawable.ic_category_coffee_24px);
                break;
            case "Concierto":
                icon.setImageResource(R.drawable.ic_category_concert_24px);
                break;
            case "Culinario":
                icon.setImageResource(R.drawable.ic_category_culinary_24px);
                break;
            case "Fiesta":
                icon.setImageResource(R.drawable.ic_category_fest_24px);
                break;
            case "Galería":
                icon.setImageResource(R.drawable.ic_category_gallery_24px);
                break;
            case "Ocio":
                icon.setImageResource(R.drawable.ic_category_leisure_24px);
                break;
            case "Lectura":
                icon.setImageResource(R.drawable.ic_category_library_24px);
                break;
            case "Película":
                icon.setImageResource(R.drawable.ic_category_movie_24px);
                break;
            case "Venta/ofertas":
                icon.setImageResource(R.drawable.ic_category_offersale_24px);
                break;
            case "Almuerzo/cena":
                icon.setImageResource(R.drawable.ic_category_dinner_24px);
                break;
            case "Deporte":
                icon.setImageResource(R.drawable.ic_category_sport_24px);
                break;
            case "Salida a terreno":
                icon.setImageResource(R.drawable.ic_category_terrain_24px);
                break;
            default:
                break;
        }

        titleEvent.setText(events.getTitle());
        placeEvent.setText(events.getPlace());
        String formatDate = new SimpleDateFormat("HH:mm", Locale.US).format(events.getDateTime())+" hrs.";
        dateEvent.setText(formatDate);
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
