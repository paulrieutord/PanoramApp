package com.udp.appsproject.panoramapp.ui;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.udp.appsproject.panoramapp.R;

public class event_detail extends AppCompatActivity {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_detail_event);

        Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);

        collapsingToolbarLayout.setTitle(extras.getString(EXTRA_QUOTE));

        ((TextView)findViewById(R.id.place_value)).setText(extras.getString(EXTRA_ATTR));
    }
}
