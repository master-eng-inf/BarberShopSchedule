package com.udl.bss.barbershopschedule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.udl.bss.barbershopschedule.adapters.PriceAdapter;
import com.udl.bss.barbershopschedule.domain.Price;
import com.udl.bss.barbershopschedule.domain.Time;
import com.udl.bss.barbershopschedule.listeners.PricePreAppointmentClick;

import java.util.ArrayList;
import java.util.List;

public class BarberServicePricesActivity extends AppCompatActivity {

    private Time time;
    private RecyclerView pricesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_service_prices);

        Intent intent = getIntent();
        time = intent.getParcelableExtra("Time");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.pricesRecyclerView = findViewById(R.id.service_prices);

        if(this.pricesRecyclerView != null)
        {
            this.pricesRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            this.pricesRecyclerView.setLayoutManager(llm);

            setPricesItems();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    private void setPricesItems() {
        List<Price> pricesList = new ArrayList<>();

        Price price1 = new Price(1, "TODO", "Hair cut", 7);
        Price price2 = new Price(2, "TODO", "Tint",12);

        pricesList.add(price1);
        pricesList.add(price2);

        PriceAdapter adapter = new PriceAdapter(pricesList, new PricePreAppointmentClick((Activity)this, pricesRecyclerView));
        pricesRecyclerView.setAdapter(adapter);
    }
}
