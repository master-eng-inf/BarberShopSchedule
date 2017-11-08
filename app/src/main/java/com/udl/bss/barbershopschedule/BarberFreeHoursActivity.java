package com.udl.bss.barbershopschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.udl.bss.barbershopschedule.adapters.GeneralAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BarberFreeHoursActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_free_hours);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    private List<Object> GetElements()
    {
        List<Object> lstToReturn = new ArrayList<>();

        Date d1 = new Date();
        d1.setHours(10);
        d1.setMinutes(00);

        Date d2 = new Date();
        d1.setHours(12);
        d1.setMinutes(00);

        Date d3 = new Date();
        d1.setHours(17);
        d1.setMinutes(00);

        Date d4 = new Date();
        d1.setHours(18);
        d1.setMinutes(00);

        Date d5 = new Date();
        d1.setHours(18);
        d1.setMinutes(30);

        lstToReturn.add(d1);
        lstToReturn.add(d2);
        lstToReturn.add(d3);
        lstToReturn.add(d4);
        lstToReturn.add(d5);

        return lstToReturn;
    }
}
