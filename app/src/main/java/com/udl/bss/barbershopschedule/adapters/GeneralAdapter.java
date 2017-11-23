package com.udl.bss.barbershopschedule.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.domain.BarberService;

import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 08/11/2017.
 */

public class GeneralAdapter extends ArrayAdapter<Object> {

    private int resource;

    public GeneralAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public GeneralAdapter(Context context, int resource, List<Object> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(this.resource, null);
        }

        Object object = getItem(position);

        if (object != null) {

            switch (this.resource) {
                case R.layout.barber_services_layout:
                    BarberService barberService = (BarberService) object;

                    TextView description_label = (TextView) v.findViewById(R.id.service_desctiption);
                    TextView price_label = (TextView) v.findViewById(R.id.service_price);

                    if (description_label != null) {
                        description_label.setText(barberService.GetName());
                    }

                    if (price_label != null) {
                        price_label.setText(barberService.GetPrice() + " €");
                    }

                    break;

                case R.layout.barber_free_hours_layout:
                    Date date = (Date) object;

                    TextView free_hour_label = (TextView) v.findViewById(R.id.barbe_free_hour);
                    if (free_hour_label != null) {
                        free_hour_label.setText(date.getHours()+":"+date.getMinutes());
                    }

                    break;
                default:
                    break;
            }
        }
        return v;
    }
}
