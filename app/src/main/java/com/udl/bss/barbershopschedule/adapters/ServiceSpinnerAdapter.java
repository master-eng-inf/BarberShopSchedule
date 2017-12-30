package com.udl.bss.barbershopschedule.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udl.bss.barbershopschedule.domain.BarberService;

/**
 * Created by gerard on 30/12/17.
 */

public class ServiceSpinnerAdapter extends ArrayAdapter<BarberService> {

    private Context context;
    private BarberService[] values;

    public ServiceSpinnerAdapter(Context context, int textViewResourceId, BarberService[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount(){
        return values.length;
    }

    @Override
    public BarberService getItem(int position){
        return values[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextSize(20);
        label.setText(values[position].getName());
        return label;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setTextSize(20);
        label.setText(values[position].getName());
        return label;
    }

}
