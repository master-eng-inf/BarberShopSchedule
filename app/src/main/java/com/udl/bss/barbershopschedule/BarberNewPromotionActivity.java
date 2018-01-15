package com.udl.bss.barbershopschedule;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.adapters.ServiceSpinnerAdapter;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import java.util.List;

public class BarberNewPromotionActivity extends AppCompatActivity {

    private EditText new_promotion_name, new_promotion_description;

    private SharedPreferences mPrefs;
    private Barber barber;
    private BarberService service;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_new_promotion);

        mPrefs = getSharedPreferences("USER", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        barber = gson.fromJson(json, Barber.class);

        new_promotion_name = findViewById(R.id.new_promotion_name);
        new_promotion_description = findViewById(R.id.new_promotion_description);
        checkBox = findViewById(R.id.checkbox_promotional);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AlertDialog alert = new AlertDialog.Builder(BarberNewPromotionActivity.this).create();
                    alert.setTitle(getString(R.string.promotional_title));
                    alert.setMessage(getString(R.string.promotional));
                    alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept_button),
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { }
                    });
                    alert.show();
                }
            }
        });

        final Spinner spinner = findViewById(R.id.service_spinner);
        APIController.getInstance().getServicesByBarber(barber.getToken(), String.valueOf(barber.getId()))
                .addOnCompleteListener(new OnCompleteListener<List<BarberService>>() {
            @Override
            public void onComplete(@NonNull Task<List<BarberService>> task) {
                List<BarberService> serviceList = task.getResult();
                BarberService[] servicesArray = new BarberService[serviceList.size()];
                servicesArray = serviceList.toArray(servicesArray);

                if (serviceList.size() > 0) service = serviceList.get(0);

                final ServiceSpinnerAdapter adapter = new ServiceSpinnerAdapter(
                        getApplicationContext(),
                        android.R.layout.simple_spinner_item,
                        servicesArray
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        service = adapter.getItem(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {}
                });
            }
        });


        Button btn_create = findViewById(R.id.btn_create);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertInDB();
            }
        });

        Button btn_cancel = findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void insertInDB () {

        if (creationCheck()) {

            if (barber != null) {

                int promotional = checkBox.isChecked() ? 1 : 0;

                Promotion newPromotion = new Promotion(
                        barber.getId(),
                        service.getId(),
                        new_promotion_name.getText().toString(),
                        new_promotion_description.getText().toString(),
                        promotional
                );

                APIController.getInstance().createPromotion(barber.getToken(), newPromotion);

                Toast.makeText(getApplicationContext(), getString(R.string.promotion_create), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity();
                } else {
                    finish();
                }
            }

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.field_error), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean creationCheck () {

        return new_promotion_name != null && !new_promotion_name.getText().toString().equals("")
                && new_promotion_description != null && !new_promotion_description.getText().toString().equals("")
                && service != null;

    }

}

