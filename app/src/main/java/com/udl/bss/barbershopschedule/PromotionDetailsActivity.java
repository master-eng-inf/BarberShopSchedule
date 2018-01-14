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

public class PromotionDetailsActivity extends AppCompatActivity {

    private Promotion promotion;
    private String token;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        promotion = getIntent().getParcelableExtra("promotion");

        TextView description_cv = findViewById(R.id.description_cv);
        final TextView service_cv = findViewById(R.id.service_cv);
        TextView is_promotional_cv = findViewById(R.id.is_promotional_cv);
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

        if (promotion != null) {

            setTitle(promotion.getName());
            description_cv.setText(promotion.getDescription());

            APIController.getInstance().getServiceById(token, String.valueOf(promotion.getService_id()))
                    .addOnCompleteListener(new OnCompleteListener<BarberService>() {
                        @Override
                        public void onComplete(@NonNull Task<BarberService> task) {
                            BarberService barberService = task.getResult();
                            service_cv.setText(barberService.getName());
                        }
                    });

            String promo = promotion.getIs_Promotional() == 0 ?
                    getString(R.string.no) : getString(R.string.yes);
            is_promotional_cv.setText(promo);

        }


        btn_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(getApplicationContext(), PromotionEditActivity.class);
                intent.putExtra("promotion", promotion);
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

            APIController.getInstance().getAppointmentsByBarber(token, String.valueOf(promotion.getBarber_shop_id()))
                    .addOnCompleteListener(new OnCompleteListener<List<Appointment>>() {
                @Override
                public void onComplete(@NonNull Task<List<Appointment>> task) {
                    boolean relationed_data = false;
                    String result = getString(R.string.remove_promotion);

                    final List<Appointment> appointmentList = task.getResult();
                    for (Appointment appointment: appointmentList) {
                        if (appointment.getServiceId() == promotion.getId()) {
                            relationed_data = true;
                            result += "- Appointment " + appointment.getId() + "\n";
                        }
                    }

                    if (relationed_data) result += getString(R.string.delete_promotion_dialog);
                    else result = getString(R.string.no_data) + getString(R.string.delete_promotion_dialog);

                    AlertDialog alert = new AlertDialog.Builder(PromotionDetailsActivity.this).create();
                    alert.setTitle(getString(R.string.delete_promotion_dialog_title));
                    alert.setMessage(result);
                    alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            for (Appointment appointment: appointmentList) {
                                APIController.getInstance().cancelAppointment(token, String.valueOf(appointment.getId()));
                            }
                            APIController.getInstance().removePromotion(token, String.valueOf(promotion.getId()));
                            Toast.makeText(getApplicationContext(), getString(R.string.promotion_delete), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("user", "Barber");
                            startActivity(intent);
                            finish();
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

        }
        return super.onOptionsItemSelected(item);
    }

}
