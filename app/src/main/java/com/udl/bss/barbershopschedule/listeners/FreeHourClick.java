package com.udl.bss.barbershopschedule.listeners;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.udl.bss.barbershopschedule.BarberServicePricesActivity;
import com.udl.bss.barbershopschedule.adapters.FreeHoursAdapter;

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

        Intent intent = new Intent(activity, BarberServicePricesActivity.class);
        intent.putExtra("date", adapter.getItem(position));

        activity.startActivity(intent);
    }
}
