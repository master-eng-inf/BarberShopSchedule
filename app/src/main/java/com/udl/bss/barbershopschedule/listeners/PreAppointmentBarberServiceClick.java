package com.udl.bss.barbershopschedule.listeners;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.BarberFreeHoursActivity;
import com.udl.bss.barbershopschedule.PriceDetailActivity;
import com.udl.bss.barbershopschedule.adapters.PriceAdapter;
import com.udl.bss.barbershopschedule.adapters.ServiceAdapter;
import com.udl.bss.barbershopschedule.domain.Barber;

/**
 * Created by Alex on 11/11/2017.
 */

public class PreAppointmentBarberServiceClick implements OnItemClickListener {
    private RecyclerView recyclerView;
    private Activity activity;
    private String date;

    public PreAppointmentBarberServiceClick(Activity activity, RecyclerView recyclerView, String date) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.date = date;
    }

    @Override
    public void onItemClick(View view, int position) {
        ServiceAdapter adapter = (ServiceAdapter) recyclerView.getAdapter();

        Intent intent = new Intent(activity, BarberFreeHoursActivity.class);
        intent.putExtra("service", adapter.getItem(position));
        intent.putExtra("date", this.date);

        activity.startActivity(intent);
    }
}
