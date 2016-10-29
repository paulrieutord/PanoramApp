package com.udp.appsproject.panoramapp.model;

import com.udp.appsproject.panoramapp.R;
import java.util.ArrayList;
import java.util.List;

public class EventsData {
    private static final String[] names = {
            "Pedro Ramirez",
            "Juan Carrasco",
            "Diego Gonzalez",
            "Carlos Martinez",
            "Sebastián Pedrero"

    };

    private static final String[] action = {
            "Creó este evento",
            "Asistirá a este evento.",
            "Le interesa este evento.",
            "Eliminó este evento",
            "Asistirá a este evento"
    };

    private static final String[] timeAction = {
            "12:45 hrs.",
            "20:00 hrs.",
            "13:30 hrs.",
            "23:00 hrs.",
            "22:30 hrs."
    };

    private static final int photo = R.drawable.ic_menu_events;

    private static final int avatar = android.R.drawable.sym_def_app_icon;

    private static final String[] titleEvent = {
            "Feria de comida 2016",
            "Nirvana is back",
            "Feria de cualquier cosa",
            "Carrete de electrónica",
            "Fiesta empresarial privada"
    };

    private static final String[] dateEvent = {
            "22 de mayo del 2016",
            "13 de marzo del 2016",
            "18 de junio del 2016",
            "29 de diciembre del 2016",
            "1 de febrero del 2016"
    };

    private static final String[] places = {
            "Mercado Central, Santiago, Chile",
            "Estadio Nacional, Santiago, Chile",
            "Parque Forestal, Santiago, Chile",
            "Espacio Riesco, Santiago, Chile",
            "Hotel W, Santiago, Chile"
    };

    public static List<EventItem> getListData() {
        List<EventItem> data = new ArrayList<>();

        for (int x = 0; x < 4; x++) {
            for (int i = 0; i < names.length; i++) {
                EventItem item = new EventItem();
                item.setAvatar(avatar);
                item.setName(names[i]);
                item.setAction(action[i]);
                item.setDateEvent(dateEvent[i]);
                item.setPhoto(photo);
                item.setPlace(places[i]);
                item.setTimeAction(timeAction[i]);
                item.setTitleEvent(titleEvent[i]);
                data.add(item);
            }
        }
        return data;
    }
}