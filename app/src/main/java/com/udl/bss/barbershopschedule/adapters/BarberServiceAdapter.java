package com.udl.bss.barbershopschedule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.domain.BarberService;

import java.util.List;

/**
 * Created by Alex on 08/11/2017.
 */

public class BarberServiceAdapter extends ArrayAdapter<BarberService> {

    public BarberServiceAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public BarberServiceAdapter(Context context, int resource, List<BarberService> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.barber_services_layout, null);
        }

        BarberService barber_service = getItem(position);

        if (barber_service != null) {
            TextView description_label = (TextView) v.findViewById(R.id.service_desctiption);
            TextView price_label = (TextView) v.findViewById(R.id.service_price);

            if (description_label != null) {
                description_label.setText(barber_service.Get_Description());
            }

            if (price_label != null) {
                price_label.setText(barber_service.Get_Price() + " €");
            }
        }

        return v;
    }
}
