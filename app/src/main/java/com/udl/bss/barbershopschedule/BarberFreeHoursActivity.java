package com.udl.bss.barbershopschedule;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.udl.bss.barbershopschedule.adapters.FreeHoursAdapter;
import com.udl.bss.barbershopschedule.domain.Time;
import com.udl.bss.barbershopschedule.listeners.FreeHourClick;

import java.util.ArrayList;
import java.util.List;

public class BarberFreeHoursActivity extends AppCompatActivity {

    private RecyclerView freeHoursRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_free_hours);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.freeHoursRecycleView = findViewById(R.id.free_hours);

        if(this.freeHoursRecycleView != null)
        {
            this.freeHoursRecycleView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            this.freeHoursRecycleView.setLayoutManager(llm);

            SetFreeHours();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    private void SetFreeHours()
    {
        List<Time> freeHoursList = new ArrayList<>();

        freeHoursList.add(new Time(2017, 11, 11, 10, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 10, 30, false));
        freeHoursList.add(new Time(2017, 11, 11, 11, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 11, 30, true));
        freeHoursList.add(new Time(2017, 11, 11, 12, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 12, 30, true));
        freeHoursList.add(new Time(2017, 11, 11, 13, 00, false));
        freeHoursList.add(new Time(2017, 11, 11, 15, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 15, 30, true));
        freeHoursList.add(new Time(2017, 11, 11, 16, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 16, 30, true));
        freeHoursList.add(new Time(2017, 11, 11, 17, 00, false));
        freeHoursList.add(new Time(2017, 11, 11, 17, 30, false));
        freeHoursList.add(new Time(2017, 11, 11, 18, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 18, 30, true));
        freeHoursList.add(new Time(2017, 11, 11, 19, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 19, 30, false));

        FreeHoursAdapter adapter = new FreeHoursAdapter(freeHoursList, new FreeHourClick((Activity)this, this.freeHoursRecycleView));
        this.freeHoursRecycleView.setAdapter(adapter);
    }
}
