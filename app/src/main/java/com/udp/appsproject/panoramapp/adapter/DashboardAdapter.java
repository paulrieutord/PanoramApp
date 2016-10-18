package com.udp.appsproject.panoramapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.model.EventItem;
import com.udp.appsproject.panoramapp.ui.fm_dashboard;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardHolder> {

    private List<EventItem> listData;
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public DashboardAdapter (List<EventItem> listData, fm_dashboard c) {
        this.inflater = LayoutInflater.from(c.getContext());
        this.listData = listData;
    }

    @Override
    public DashboardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.dashboard_cardview, parent, false);
        return new DashboardHolder(view);
    }

    @Override
    public void onBindViewHolder(DashboardHolder holder, int position) {
        EventItem item = listData.get(position);
        holder.name.setText(item.getName());
        holder.action.setText(item.getAction());
        holder.timeAction.setText(item.getTimeAction());
        holder.titleEvent.setText(item.getTitleEvent());
        holder.dateEvent.setText(item.getDateEvent());
        holder.place.setText(item.getPlace());
    }

    public void setListData(ArrayList<EventItem> exerciseList) {
        this.listData.clear();
        this.listData.addAll(exerciseList);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class DashboardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView avatar;
        private ImageView photo;
        private TextView name;
        private TextView action;
        private TextView timeAction;
        private TextView titleEvent;
        private TextView dateEvent;
        private TextView place;

        public DashboardHolder(View itemView) {
            super(itemView);

            avatar = (ImageView) itemView.findViewById(R.id.avatar_dashboard_item);
            photo = (ImageView) itemView.findViewById(R.id.photo_dashboard_item);
            name = (TextView) itemView.findViewById(R.id.name_dashboard_item);
            action = (TextView) itemView.findViewById(R.id.action_dashboard_item);
            timeAction = (TextView) itemView.findViewById(R.id.timeAction_dashboard_item);
            titleEvent = (TextView) itemView.findViewById(R.id.titleEvent_dashboard_item);
            dateEvent = (TextView) itemView.findViewById(R.id.dateEvent_dashboard_item);
            place = (TextView) itemView.findViewById(R.id.place_dashboard_item);

            photo.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.photo_dashboard_item) {
                itemClickCallback.onItemClick(getAdapterPosition());
            }
        }
    }
}