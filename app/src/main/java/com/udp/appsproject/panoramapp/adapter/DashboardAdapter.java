package com.udp.appsproject.panoramapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.model.DashboardItem;
import com.udp.appsproject.panoramapp.ui.fm_dashboard;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardHolder> {

    private List<DashboardItem> listData;
    private LayoutInflater inflater;

    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);
        void onSecondaryIconClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public DashboardAdapter (List<DashboardItem> listData, fm_dashboard c) {
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
        DashboardItem item = listData.get(position);
        holder.title.setText(item.getTitle());
        holder.subTitle.setText(item.getSubTitle());
        /*if (item.isFavourite()){
            holder.favouriteIcon.setImageResource(R.drawable.ic_star_black_24dp);
        } else {
            holder.favouriteIcon.setImageResource(R.drawable.ic_star_border_black_24dp);
        }*/
    }

    public void setListData(ArrayList<DashboardItem> exerciseList) {
        this.listData.clear();
        this.listData.addAll(exerciseList);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class DashboardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView icon;
        private TextView title;
        private TextView subTitle;
        //private ImageView favouriteIcon;

        private View container;
        private Button load;

        public DashboardHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.icon_dashboard_item);
            title = (TextView) itemView.findViewById(R.id.title_dashboard_item);
            subTitle = (TextView) itemView.findViewById(R.id.subtitle_dashboard_item);
            //favouriteIcon = (ImageView) itemView.findViewById(R.id.favourite_icon);
            //favouriteIcon.setOnClickListener(this);
            container = itemView.findViewById(R.id.content_dashboard_item);
            //container.setOnClickListener(this);
            load = (Button) itemView.findViewById(R.id.btn_card_load);
            load.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_card_load) {
                itemClickCallback.onItemClick(getAdapterPosition());
            } else {
                //itemClickCallback.onSecondaryIconClick(getAdapterPosition());
            }
        }
    }
}