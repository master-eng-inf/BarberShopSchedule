package com.udl.bss.barbershopschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.adapters.ServiceAdapter;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.listeners.PreAppointmentBarberServiceClick;

import java.util.ArrayList;
import java.util.Date;

public class BarberServicePricesActivity extends AppCompatActivity {

    private int barber_shop_id;
    private RecyclerView servicesRecyclerView;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_service_prices);

        date = getIntent().getStringExtra("date");
        barber_shop_id = getIntent().getIntExtra("barber_shop_id",-1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.servicesRecyclerView = findViewById(R.id.service_prices);

        if (this.servicesRecyclerView != null) {
            this.servicesRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            this.servicesRecyclerView.setLayoutManager(llm);

            setPricesItems();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    private void setPricesItems() {
        BLL instance = new BLL(this);
        ArrayList<BarberService> barber_shop_services = instance.Get_BarberShopServices(barber_shop_id);

        ServiceAdapter adapter = new ServiceAdapter(barber_shop_services, new PreAppointmentBarberServiceClick(this, servicesRecyclerView, date), this);
        servicesRecyclerView.setAdapter(adapter);
    }
}
