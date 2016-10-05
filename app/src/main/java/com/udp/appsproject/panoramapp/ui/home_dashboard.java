package com.udp.appsproject.panoramapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udp.appsproject.panoramapp.R;
import com.udp.appsproject.panoramapp.adapter.DashboardAdapter;
import com.udp.appsproject.panoramapp.model.DashboardData;

public class home_dashboard extends AppCompatActivity {

    private RecyclerView recView_dashboard;
    private DashboardAdapter adapter_dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dashboard);

        recView_dashboard = (RecyclerView) findViewById(R.id.recView_dashboard);
        recView_dashboard.setLayoutManager(new LinearLayoutManager(this));

        adapter_dashboard = new DashboardAdapter(DashboardData.getListData(), this);
        recView_dashboard.setAdapter(adapter_dashboard);
    }
}