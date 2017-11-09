package com.udl.bss.barbershopschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BarberHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_home);


        /*Listener: Navigation to barber schedule*/
        Button barber_schedule_btn = (Button) findViewById(R.id.barber_schedule_btn);

        barber_schedule_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BarberScheduleActivity.class);
                startActivity(intent);
            }
        });


        /*Listener: Navigation to barber services list*/
        Button barber_service_btn = (Button) findViewById(R.id.barber_service_btn);

        barber_service_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BarberServicesActivity.class);
                startActivity(intent);
            }
        });


        /*Listener: Navigation to barber promotion list*/
        Button barber_promotion_btn = (Button) findViewById(R.id.barber_promotion_btn);

        barber_promotion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BarberPromotionsActivity.class);
                startActivity(intent);
            }
        });

    }
}
