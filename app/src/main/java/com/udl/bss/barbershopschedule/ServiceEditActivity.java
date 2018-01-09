package com.udl.bss.barbershopschedule;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

public class ServiceEditActivity extends AppCompatActivity {

    private EditText name_cv, price_cv, duration_cv;
    private Barber barber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_edit);

        SharedPreferences mPrefs = getSharedPreferences("USER", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        barber = gson.fromJson(json, Barber.class);

        final BarberService service = getIntent().getParcelableExtra("service");

        name_cv = findViewById(R.id.name_cv);
        price_cv = findViewById(R.id.price_cv);
        duration_cv = findViewById(R.id.duration_cv);
        Button btn_update = findViewById(R.id.btn_update);

        name_cv.setText(service.getName());
        price_cv.setText(String.valueOf(service.getPrice()));
        duration_cv.setText(String.valueOf(service.getDuration()));

        btn_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if (creationCheck()) {
                    if(creationDurationCheck()) {

                        AlertDialog alert = new AlertDialog.Builder(ServiceEditActivity.this).create();
                        alert.setTitle(getString(R.string.update_title_alert));
                        alert.setMessage(getString(R.string.update_service_dialog));
                        alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                BarberService serviceUpdated = new BarberService(
                                        service.getId(),
                                        service.getBarberShopId(),
                                        name_cv.getText().toString(),
                                        Double.parseDouble(price_cv.getText().toString()),
                                        Double.parseDouble(duration_cv.getText().toString()));

                                APIController.getInstance().updateService(barber.getToken(), serviceUpdated);
                                Toast.makeText(getApplicationContext(), getString(R.string.service_update), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.field_duration_error), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.field_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private boolean creationCheck () {

        return name_cv != null && !name_cv.getText().toString().equals("")
                && price_cv != null && !price_cv.getText().toString().equals("")
                && duration_cv != null && !duration_cv.getText().toString().equals("");
    }

    private boolean creationDurationCheck() {
        Double durationDouble = Double.parseDouble(duration_cv.getText().toString());
        return durationDouble%15 == 0;
    }
}
