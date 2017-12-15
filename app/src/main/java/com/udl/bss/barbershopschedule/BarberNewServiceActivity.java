package com.udl.bss.barbershopschedule;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.BarberService;


public class BarberNewServiceActivity extends AppCompatActivity {

    private EditText new_service_name, new_service_price, new_service_duration;
    private String new_service_name_var;
    private double new_service_price_var;
    private double new_service_duration_var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_new_service);

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

            new_service_name_var = new_service_name.getText().toString();
            new_service_price_var = Double.parseDouble(new_service_price.getText().toString());
            new_service_duration_var = Double.parseDouble(new_service_duration.getText().toString());

            BLL instance = new BLL(this);

            BarberService new_service = new BarberService(-1,0,new_service_name_var,new_service_price_var,new_service_duration_var);

            instance.Insert_Service(new_service);

            Toast.makeText(getApplicationContext(), "Your service was created succesfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("user", "Barber");
            this.startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.field_error), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean creationCheck () {

        return new_service_name != null && !new_service_name.getText().toString().equals("")
                && new_service_price != null && !new_service_price.getText().toString().equals("")
                && new_service_duration != null && !new_service_duration.getText().toString().equals("");

    }

}
