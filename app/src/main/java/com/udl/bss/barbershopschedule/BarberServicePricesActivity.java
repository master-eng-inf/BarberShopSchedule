package com.udl.bss.barbershopschedule;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.adapters.ServiceAdapter;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.listeners.PreAppointmentBarberServiceClick;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import java.util.List;

public class BarberServicePricesActivity extends AppCompatActivity {

    private int barber_shop_id;
    private RecyclerView servicesRecyclerView;
    private String date;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_service_prices);

        date = getIntent().getStringExtra("date");
        barber_shop_id = getIntent().getIntExtra("barber_shop_id",-1);

        Toolbar toolbar = findViewById(R.id.toolbar);
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

        SharedPreferences mPrefs = getSharedPreferences("USER", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        final Client client = gson.fromJson(json, Client.class);

        APIController.getInstance().getServicesByBarber(client.getToken(), String.valueOf(barber_shop_id))
                .addOnCompleteListener(new OnCompleteListener<List<BarberService>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<BarberService>> task) {

                        ServiceAdapter adapter = new ServiceAdapter(task.getResult(), new PreAppointmentBarberServiceClick(activity, servicesRecyclerView, date), activity);
                        servicesRecyclerView.setAdapter(adapter);
                    }
                });
    }
}
