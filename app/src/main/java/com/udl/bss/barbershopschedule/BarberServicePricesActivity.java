package com.udl.bss.barbershopschedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.BarberServiceAdapter;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.BarberService;

import java.util.ArrayList;
import java.util.List;

public class BarberServicePricesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_service_prices);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.service_prices);

        BarberServiceAdapter bsa = new BarberServiceAdapter(this, R.layout.barber_services_layout, GetElements());

        listView.setAdapter(bsa);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BarberService service = (BarberService) parent.getItemAtPosition(position);
                Toast.makeText(getApplication(),"This will create your appointment for a " + service.Get_Description() ,Toast.LENGTH_SHORT).show();
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

    private List<BarberService> GetElements()
    {
        List<BarberService> lstToReturn = new ArrayList<>();

        lstToReturn.add(new BarberService(0, "Man haircut", 10));
        lstToReturn.add(new BarberService(1, "Kid haircut", 5));
        lstToReturn.add(new BarberService(2, "Girl haircut", 5));
        lstToReturn.add(new BarberService(3, "Woman haircut", 10));

        return lstToReturn;
    }
}
