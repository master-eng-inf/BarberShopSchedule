package com.udl.bss.barbershopschedule.listeners;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.BarberServicePricesActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.FreeHoursAdapter;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Time;

/**
 * Created by Alex on 10/11/2017.
 */

public class FreeHourClick implements OnItemClickListener {

    private Activity activity;
    private RecyclerView recyclerView;
    private BarberService service;

    public FreeHourClick(Activity activity, RecyclerView recyclerView, BarberService service) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.service = service;
    }

    @Override
    public void onItemClick(View view, int position) {
        FreeHoursAdapter adapter = (FreeHoursAdapter) recyclerView.getAdapter();

        Time time = adapter.getItem(position);

        if (!time.GetAvailability()) {
            Toast.makeText(activity, R.string.unavailable_time, Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(activity, "This will create an appointment for " + service.Get_Name(), Toast.LENGTH_SHORT).show();
        }
    }
}
