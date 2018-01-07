package com.udl.bss.barbershopschedule;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;


public class AppointmentDetailsActivity extends AppCompatActivity {

    FloatingActionButton delete_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
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

        delete_btn = findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alert = new AlertDialog.Builder(AppointmentDetailsActivity.this).create();
                alert.setTitle(getString(R.string.delete_appointment_dialog_title));
                alert.setMessage(getString(R.string.delete_appointment_dialog));
                alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), getString(R.string.appointment_deleted), Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);
            fade.excludeTarget(R.id.delete_btn, true);

            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }

        ScaleAnimation anim = new ScaleAnimation(0,1,0,1);
        anim.setFillBefore(true);
        anim.setFillAfter(true);
        anim.setFillEnabled(true);
        anim.setDuration(300);
        anim.setInterpolator(new OvershootInterpolator());
        delete_btn.startAnimation(anim);

        TextView tv_date = findViewById(R.id.date_detail);
        final TextView tv_service = findViewById(R.id.service_detail);
        final CollapsingToolbarLayout ctl = findViewById(R.id.toolbar_layout);

        Appointment a = getIntent().getParcelableExtra("appointment");

        APIController.getInstance().getBarberById(token, String.valueOf(a.getBarber_shop_id())).addOnCompleteListener(new OnCompleteListener<Barber>() {
            @Override
            public void onComplete(@NonNull Task<Barber> task) {
                Barber barber = task.getResult();
                ctl.setTitle(barber.getName());
            }
        });

        APIController.getInstance().getServiceById(token, String.valueOf(a.getService_id())).addOnCompleteListener(new OnCompleteListener<BarberService>() {
            @Override
            public void onComplete(@NonNull Task<BarberService> task) {
                BarberService service = task.getResult();
                tv_service.setText(service.getName());
            }
        });

        //TODO format date
        tv_date.setText(a.getDate());

    }
}
