package com.udp.appsproject.panoramapp.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.adapter.DashboardAdapter;
import com.udp.appsproject.panoramapp.model.DashboardData;
import com.udp.appsproject.panoramapp.model.DashboardItem;
import java.util.ArrayList;

public class home_dashboard extends AppCompatActivity implements DashboardAdapter.ItemClickCallback{

    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    private RecyclerView recView_dashboard;
    private DashboardAdapter adapter_dashboard;
    private ArrayList listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dashboard);

        listData = (ArrayList) DashboardData.getListData();

        recView_dashboard = (RecyclerView) findViewById(R.id.recView_dashboard);
        recView_dashboard.setLayoutManager(new LinearLayoutManager(this));

        adapter_dashboard = new DashboardAdapter(DashboardData.getListData(), this);
        recView_dashboard.setAdapter(adapter_dashboard);
        adapter_dashboard.setItemClickCallback(this);
    }

    @Override
    public void onItemClick(int p) {
        DashboardItem item = (DashboardItem) listData.get(p);

        Intent i = new Intent(this, event_detail.class);

        Bundle extras = new Bundle();
        extras.putString(EXTRA_QUOTE, item.getTitle());
        extras.putString(EXTRA_ATTR, item.getSubTitle());
        i.putExtra(BUNDLE_EXTRAS, extras);

        startActivity(i);
    }

    @Override
    public void onSecondaryIconClick(int p) {
        DashboardItem item = (DashboardItem) listData.get(p);

        if (item.isFavourite()){
            item.setFavourite(false);
        } else {
            item.setFavourite(true);
        }

        adapter_dashboard.setListData(listData);
        adapter_dashboard.notifyDataSetChanged();
    }
}