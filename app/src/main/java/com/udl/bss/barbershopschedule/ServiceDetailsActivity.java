package com.udl.bss.barbershopschedule;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

import java.util.List;

public class ServiceDetailsActivity extends AppCompatActivity {

    private BarberService service;
    private String token;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        service = getIntent().getParcelableExtra("service");

        TextView price_cv = findViewById(R.id.price_cv);
        TextView duration_cv = findViewById(R.id.duration_cv);
        FloatingActionButton btn_edit = findViewById(R.id.edit_btn);

        SharedPreferences mPrefs = getSharedPreferences("USER", MODE_PRIVATE);
        mode = mPrefs.getString("mode", "");
        String json = mPrefs.getString("user", "");
        Gson gson = new Gson();

        if(mode.equals("User")) {
            btn_edit.setVisibility(View.GONE);
            Client client = gson.fromJson(json, Client.class);
            token = client.getToken();
        } else {
            Barber barber = gson.fromJson(json, Barber.class);
            token = barber.getToken();
        }

        if (service != null) {

            setTitle(service.getName());

            String price = Double.toString(service.getPrice());
            price_cv.setText(price);

            String duration = Double.toString(service.getDuration());
            duration_cv.setText(duration);

        }



        btn_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(getApplicationContext(), ServiceEditActivity.class);
                intent.putExtra("service", service);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mode != null && !mode.equals("User")){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_service_details, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onOptionsItemSelected(item)) {
                onBackPressed();
            }
            return true;
        } else if (id == R.id.delete_btn) {

            APIController.getInstance().getPromotionsByBarber(token,
                    String.valueOf(service.getBarberShopId()))
                    .addOnCompleteListener(new OnCompleteListener<List<Promotion>>() {
                @Override
                public void onComplete(@NonNull Task<List<Promotion>> task) {
                    final boolean[] relationed_data = {false};
                    final String[] result = {getString(R.string.remove_service)};

                    for (Promotion promotion: task.getResult()) {
                        if (promotion.getService_id() == service.getId()) {
                            relationed_data[0] = true;
                            result[0] += "- " + promotion.getName() + "\n";
                        }
                    }

                    APIController.getInstance().getAppointmentsByBarber(token, String.valueOf(service.getBarberShopId()))
                            .addOnCompleteListener(new OnCompleteListener<List<Appointment>>() {
                        @Override
                        public void onComplete(@NonNull Task<List<Appointment>> task) {
                            final List<Appointment> appointmentList = task.getResult();
                            for (Appointment appointment: appointmentList) {
                                if (appointment.getServiceId() == service.getId()) {
                                    relationed_data[0] = true;
                                    result[0] += "- Appointment " + appointment.getId() + "\n";
                                }
                            }

                            if (relationed_data[0]) result[0] += getString(R.string.delete_service_dialog);
                            else result[0] = getString(R.string.no_data) + getString(R.string.delete_service_dialog);

                            AlertDialog alert = new AlertDialog.Builder(ServiceDetailsActivity.this).create();
                            alert.setTitle(getString(R.string.delete_service_dialog_title));
                            alert.setMessage(result[0]);
                            alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept_button),
                                    new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    for (Appointment appointment: appointmentList) {
                                        APIController.getInstance().cancelAppointment(token, String.valueOf(appointment.getId()));
                                    }
                                    APIController.getInstance().removeService(token, String.valueOf(service.getId()));
                                    Toast.makeText(getApplicationContext(), getString(R.string.service_delete), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            alert.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel_button),
                                    new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alert.show();

                        }
                    });

                }
            });

        }
        return super.onOptionsItemSelected(item);
    }

}
