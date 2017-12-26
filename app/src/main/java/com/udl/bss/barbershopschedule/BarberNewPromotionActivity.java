package com.udl.bss.barbershopschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Promotion;

public class BarberNewPromotionActivity extends AppCompatActivity {

    private EditText new_promotion_name, new_promotion_description, new_promotion_service;
    private String new_promotion_name_var;
    private String new_promotion_description_var;
    private int new_promotion_service_var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_new_promotion);

        new_promotion_name = findViewById(R.id.new_promotion_name);
        new_promotion_description = findViewById(R.id.new_promotion_description);
        new_promotion_service = findViewById(R.id.new_promotion_service);


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

            new_promotion_name_var = new_promotion_name.getText().toString();
            new_promotion_description_var = new_promotion_description.getText().toString();
            new_promotion_service_var = Integer.parseInt(new_promotion_service.getText().toString());

            BLL instance = new BLL(this);

            Promotion new_promotion = new Promotion(-1,0,new_promotion_service_var,new_promotion_name_var,new_promotion_description_var,0);

            instance.Insert_Promotion(new_promotion);

            Toast.makeText(getApplicationContext(), "Your promotion was created succesfully", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("user", "Barber");
            this.startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.field_error), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean creationCheck () {

        return new_promotion_name != null && !new_promotion_name.getText().toString().equals("")
                && new_promotion_description != null && !new_promotion_description.getText().toString().equals("")
                && new_promotion_service != null && !new_promotion_service.getText().toString().equals("");

    }

}

