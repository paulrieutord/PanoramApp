package com.udp.appsproject.panoramapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.udp.appsproject.panoramapp.R;

public class event_detail extends AppCompatActivity {
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";
    private static final String EXTRA_QUOTE = "EXTRA_QUOTE";
    private static final String EXTRA_ATTR = "EXTRA_ATTR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Bundle extras = getIntent().getBundleExtra(BUNDLE_EXTRAS);

        ((TextView)findViewById(R.id.lbl_quote_text)).setText(extras.getString(EXTRA_QUOTE));
        ((TextView)findViewById(R.id.lbl_quote_attribution)).setText(extras.getString(EXTRA_ATTR));
    }
}
