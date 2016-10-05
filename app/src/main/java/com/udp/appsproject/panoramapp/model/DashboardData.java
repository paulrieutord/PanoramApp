package com.udp.appsproject.panoramapp.model;

import java.util.ArrayList;
import java.util.List;

public class DashboardData {
    private static final String[] titles = {"Elemento uno", "Elemento dos", "Elemento tres"};
    private static final int[] icons = {android.R.drawable.ic_popup_reminder, android.R.drawable.ic_menu_add, android.R.drawable.ic_menu_delete};

    public static List<DashboardItem> getListData() {
        List<DashboardItem> data = new ArrayList<>();

        for (int x = 0; x < 4; x++) {
            for (int i = 0; i < titles.length && i < icons.length; i++) {
                DashboardItem item = new DashboardItem();
                item.setImageResId(icons[i]);
                item.setTitle(titles[i]);
                data.add(item);
            }
        }
        return data;
    }
}
