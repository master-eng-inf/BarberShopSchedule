package com.udl.bss.barbershopschedule.listeners;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.BarberServicePricesActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.FreeHoursAdapter;
import com.udl.bss.barbershopschedule.domain.Time;

/**
 * Created by Alex on 10/11/2017.
 */

public class FreeHourClick implements OnItemClickListener {

    private Activity activity;
    private RecyclerView recyclerView;

    public FreeHourClick(Activity activity, RecyclerView recyclerView) {
        this.activity = activity;
        this.recyclerView = recyclerView;
    }

    @Override
    public void onItemClick(View view, int position) {
        FreeHoursAdapter adapter = (FreeHoursAdapter) recyclerView.getAdapter();

        Time time = adapter.getItem(position);

        if (!time.GetAvailability()) {
            Toast.makeText(view.getContext(), R.string.unavailable_time, Toast.LENGTH_SHORT).show();
        }

        else {
            Intent intent = new Intent(activity, BarberServicePricesActivity.class);
            intent.putExtra("Time", adapter.getItem(position));

            activity.startActivity(intent);
        }
    }
}
