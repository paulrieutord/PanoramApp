package com.udp.appsproject.panoramapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.model.DashboardItem;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardHolder> {

    private List<DashboardItem> listData;
    private LayoutInflater inflater;

    public DashboardAdapter (List<DashboardItem> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public DashboardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.dashboard_item, parent, false);
        return new DashboardHolder(view);
    }

    @Override
    public void onBindViewHolder(DashboardHolder holder, int position) {
        DashboardItem item = listData.get(position);
        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class DashboardHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView icon;
        private View container;

        public DashboardHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.text_dashboard_item);
            icon = (ImageView) itemView.findViewById(R.id.icon_dashboard_item);
            container = itemView.findViewById(R.id.content_dashboard_item);
        }
    }
}
