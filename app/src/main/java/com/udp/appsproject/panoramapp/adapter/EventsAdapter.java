package com.udp.appsproject.panoramapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.model.EventItem;
import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsHolder> {

    private List<EventItem> listData;
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public EventsAdapter(List<EventItem> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public EventsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_item, parent, false);
        return new EventsHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsHolder holder, int position) {
        EventItem item = listData.get(position);
        holder.titleEvent.setText(item.getTitleEvent());
        holder.placeEvent.setText(item.getPlace());
        holder.dateEvent.setText(item.getTimeAction());
    }

    public void setListData(ArrayList<EventItem> exerciseList) {
        this.listData.clear();
        this.listData.addAll(exerciseList);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class EventsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView icon;
        private TextView titleEvent;
        private TextView placeEvent;
        private TextView dateEvent;

        private View container;

        public EventsHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.icon_event_item);
            titleEvent = (TextView) itemView.findViewById(R.id.title_event_item);
            placeEvent = (TextView) itemView.findViewById(R.id.place_event_item);
            dateEvent = (TextView) itemView.findViewById(R.id.time_event_item);
            container = itemView.findViewById(R.id.content_event_item);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.content_event_item) {
                itemClickCallback.onItemClick(getAdapterPosition());
            }
        }
    }
}
