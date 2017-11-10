package com.udl.bss.barbershopschedule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.udl.bss.barbershopschedule.adapters.FreeHoursAdapter;
import com.udl.bss.barbershopschedule.adapters.GeneralAdapter;
import com.udl.bss.barbershopschedule.adapters.PriceAdapter;
import com.udl.bss.barbershopschedule.listeners.FreeHourClick;
import com.udl.bss.barbershopschedule.listeners.PriceClick;

import java.util.ArrayList;
import java.util.Date;
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

        /*
        ListView listView = (ListView)findViewById(R.id.free_hours);

        GeneralAdapter ga = new GeneralAdapter(this, R.layout.barber_free_hours_layout, GetElements());
        listView.setAdapter(ga);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Date date = (Date) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), BarberServicePricesActivity.class);
                intent.putExtra("Date", date);

                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    private void SetFreeHours()
    {
        List<Date> freeHoursList = new ArrayList<>();

        Date d1 = new Date();
        d1.setHours(10);
        d1.setMinutes(00);

        Date d2 = new Date();
        d2.setHours(12);
        d2.setMinutes(00);

        Date d3 = new Date();
        d3.setHours(17);
        d3.setMinutes(00);

        Date d4 = new Date();
        d4.setHours(18);
        d4.setMinutes(00);

        Date d5 = new Date();
        d5.setHours(18);
        d5.setMinutes(30);

        freeHoursList.add(d1);
        freeHoursList.add(d2);
        freeHoursList.add(d3);
        freeHoursList.add(d4);
        freeHoursList.add(d5);

        FreeHoursAdapter adapter = new FreeHoursAdapter(freeHoursList, new FreeHourClick((Activity)this, this.freeHoursRecycleView));
        this.freeHoursRecycleView.setAdapter(adapter);
    }
}
