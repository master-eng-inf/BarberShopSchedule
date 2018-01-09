package com.udl.bss.barbershopschedule;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class PromotionEditActivity extends AppCompatActivity {

    private EditText name_cv, description_cv;
    private CheckBox checkBox;
    private Spinner spinner;
    private Barber barber;
    private BarberService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_edit);

        SharedPreferences mPrefs = getSharedPreferences("USER", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        barber = gson.fromJson(json, Barber.class);

        final Promotion promotion = getIntent().getParcelableExtra("promotion");

        name_cv = findViewById(R.id.name_cv);
        description_cv = findViewById(R.id.description_cv);
        checkBox = findViewById(R.id.checkbox_promotional);
        spinner = findViewById(R.id.service_spinner);
        Button btn_update = findViewById(R.id.btn_update);


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



        name_cv.setText(promotion.getName());
        description_cv.setText(promotion.getDescription());
        checkBox.setChecked(promotion.getIs_Promotional() == 1);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AlertDialog alert = new AlertDialog.Builder(PromotionEditActivity.this).create();
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

        btn_update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(creationCheck()) {

                    AlertDialog alert = new AlertDialog.Builder(PromotionEditActivity.this).create();
                    alert.setTitle(getString(R.string.update_title_alert));
                    alert.setMessage(getString(R.string.update_promotion_dialog));
                    alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int isPromotionalToUpdate = checkBox.isChecked() ? 1 : 0;
                            Promotion promotionUpdated = new Promotion(
                                    promotion.getId(),
                                    promotion.getBarber_shop_id(),
                                    service.getId(),
                                    name_cv.getText().toString(),
                                    description_cv.getText().toString(),
                                    isPromotionalToUpdate);

                            APIController.getInstance().updatePromotion(barber.getToken(), promotionUpdated);
                            Toast.makeText(getApplicationContext(), getString(R.string.promotion_update), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), getString(R.string.field_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private boolean creationCheck () {

        return name_cv != null && !name_cv.getText().toString().equals("")
                && description_cv != null && !description_cv.getText().toString().equals("")
                && service != null;

    }
}
