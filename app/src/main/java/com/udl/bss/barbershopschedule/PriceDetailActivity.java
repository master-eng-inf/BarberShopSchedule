package com.udl.bss.barbershopschedule;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

public class PriceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences mPrefs = getSharedPreferences("USER", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        String mode = mPrefs.getString("mode", "");

        String token;
        if (mode.equals("Barber")) {
            Barber barber = gson.fromJson(json, Barber.class);
            token = barber.getToken();
        } else {
            Client client = gson.fromJson(json, Client.class);
            token = client.getToken();
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            fade.excludeTarget(R.id.fab, true);

            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }

        ScaleAnimation anim = new ScaleAnimation(0,1,0,1);
        anim.setFillBefore(true);
        anim.setFillAfter(true);
        anim.setFillEnabled(true);
        anim.setDuration(300);
        anim.setInterpolator(new OvershootInterpolator());
        fab.startAnimation(anim);

        TextView tv_price = findViewById(R.id.price_cv);
        TextView tv_service = findViewById(R.id.service_detail);
        TextView tv_duration = findViewById(R.id.duration_cv);

        final CollapsingToolbarLayout ctl = findViewById(R.id.toolbar_layout);

        BarberService barber_shop_service = getIntent().getParcelableExtra("service");


        APIController.getInstance().getBarberById(token, String.valueOf(barber_shop_service.getBarberShopId()))
                .addOnCompleteListener(new OnCompleteListener<Barber>() {
            @Override
            public void onComplete(@NonNull Task<Barber> task) {
                Barber barber = task.getResult();
                ctl.setTitle(barber.getName());
            }
        });

        tv_service.setText(barber_shop_service.getName());
        String price = Double.toString(barber_shop_service.getPrice()) + " " + getResources().getString(R.string.service_price_currency);
        tv_price.setText(price);
        String duration = Double.toString(barber_shop_service.getDuration()) + " " + getResources().getString(R.string.service_duration);
        tv_duration.setText(duration);
    }
}
