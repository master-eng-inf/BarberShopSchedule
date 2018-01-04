package com.udl.bss.barbershopschedule;


import android.content.Intent;
import android.content.SharedPreferences;
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


public class BarberNewServiceActivity extends AppCompatActivity {

    private EditText new_service_name, new_service_price, new_service_duration;

    private SharedPreferences mPrefs;
    private Barber barber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_new_service);

        mPrefs = getSharedPreferences("USER", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        barber = gson.fromJson(json, Barber.class);

        new_service_name = findViewById(R.id.new_service_name);
        new_service_price = findViewById(R.id.new_service_price);
        new_service_duration = findViewById(R.id.new_service_duration);


        Button btn_create = (Button) findViewById(R.id.btn_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertInDB();
            }
        });

        Button btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void insertInDB () {

        if (creationCheck()) {

            if (creationDurationCheck()) {

                if (barber != null) {
                    BarberService newService = new BarberService(
                            barber.getId(),
                            new_service_name.getText().toString(),
                            Double.parseDouble(new_service_price.getText().toString()),
                            Double.parseDouble(new_service_duration.getText().toString()));

                    APIController.getInstance().createService(barber.getToken(), newService);

                    Toast.makeText(getApplicationContext(), "Your service was created succesfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("user", "Barber");
                    this.startActivity(intent);
                }
            }
            else {
                Toast.makeText(getApplicationContext(), getString(R.string.field_duration_error), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.field_error), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean creationCheck () {

        return new_service_name != null && !new_service_name.getText().toString().equals("")
                && new_service_price != null && !new_service_price.getText().toString().equals("")
                && new_service_duration != null && !new_service_duration.getText().toString().equals("");
    }

    private boolean creationDurationCheck() {
        Double durationDouble = Double.parseDouble(new_service_duration.getText().toString());
        return durationDouble%15 == 0;
    }

}
