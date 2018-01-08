package com.udl.bss.barbershopschedule;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
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
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

public class BarberAppointmentDetailsActivity extends AppCompatActivity {

    FloatingActionButton delete_btn;
    TextView tv_service,tv_client,tv_date,tv_promotion;
    private Appointment appointment;
    private SharedPreferences mPrefs;
    private Barber barber;
    private String appointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_appointment_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPrefs = getSharedPreferences("USER", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        barber = gson.fromJson(json, Barber.class);

        delete_btn = findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alert = new AlertDialog.Builder(BarberAppointmentDetailsActivity.this).create();
                alert.setTitle(getString(R.string.delete_appointment_dialog_title));
                alert.setMessage(getString(R.string.delete_appointment_dialog));
                alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                @Override
                    public void onClick(DialogInterface dialog, int which) {
                        APIController.getInstance().removeAppointment(barber.getToken(), appointmentId);
                        Toast.makeText(getApplicationContext(), getString(R.string.appointment_deleted), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("user", "Barber");
                        startActivity(intent);
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

        tv_service = findViewById(R.id.service_detail);
        tv_client = findViewById(R.id.client_detail);
        tv_promotion = findViewById(R.id.promotion_detail);
        tv_date = findViewById(R.id.date_detail);

        appointment = getIntent().getParcelableExtra("appointment");
        appointmentId = Integer.toString(appointment.getId());

        Log.d("TAG", "onCreate: " +appointment);


        String serviceId = Integer.toString(appointment.getService_id());
        APIController.getInstance().getServiceById(barber.getToken() ,serviceId)
                .addOnCompleteListener(new OnCompleteListener<BarberService>() {
                    @Override
                    public void onComplete(@NonNull Task<BarberService> task) {
                        BarberService barberService = task.getResult();
                        tv_service.setText(barberService.getName());
                    }
                });

        String clientId = Integer.toString(appointment.getClient_id());
        APIController.getInstance().getClientById(barber.getToken() ,clientId)
                .addOnCompleteListener(new OnCompleteListener<Client>() {
                    @Override
                    public void onComplete(@NonNull Task<Client> task) {
                        Client client = task.getResult();
                        tv_client.setText(client.getName());
                    }
                });

        String promotionId = Integer.toString(appointment.getPromotion_id());
        if (appointment.getPromotion_id() == -1){
            tv_promotion.setText(getString(R.string.no_promotion));
        } else {
            APIController.getInstance().getPromotionById(barber.getToken() ,promotionId)
                    .addOnCompleteListener(new OnCompleteListener<Promotion>() {
                        @Override
                        public void onComplete(@NonNull Task<Promotion> task) {
                            Promotion promotion = task.getResult();
                            tv_promotion.setText(promotion.getName());
                        }
                    });
        }


        tv_date.setText(appointment.getDate());

    }

}
