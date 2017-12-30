package com.udl.bss.barbershopschedule;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.BarberService;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppointmentDetailsActivity extends AppCompatActivity {

    FloatingActionButton delete_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        delete_btn = (FloatingActionButton) findViewById(R.id.delete_btn);
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

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
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
        TextView tv_service = findViewById(R.id.service_detail);
        CollapsingToolbarLayout ctl = findViewById(R.id.toolbar_layout);

        Appointment a = getIntent().getParcelableExtra("appointment");

        BLL instance = new BLL(this);
        Barber barber = instance.Get_BarberShop(a.getBarber_shop_id());
        BarberService service = instance.Get_BarberShopService(a.getService_id());

        //TODO format date
        ctl.setTitle(barber.getName());
        tv_date.setText(a.getDate());
        tv_service.setText(service.getName());
    }
}
