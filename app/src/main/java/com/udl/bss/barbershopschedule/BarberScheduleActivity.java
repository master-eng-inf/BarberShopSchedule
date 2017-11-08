package com.udl.bss.barbershopschedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.udl.bss.barbershopschedule.domain.Barber;

public class BarberScheduleActivity extends AppCompatActivity {

    private static final String BARBER_SHOP = "barber_shop";
    private Barber barber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_schedule);
    }
}
