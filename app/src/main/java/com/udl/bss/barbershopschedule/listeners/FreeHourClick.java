package com.udl.bss.barbershopschedule.listeners;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.BarberFreeHoursActivity;
import com.udl.bss.barbershopschedule.HomeActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.FreeHoursAdapter;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.domain.Time;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Alex on 10/11/2017.
 */

public class FreeHourClick implements OnItemClickListener {

    private Activity activity;
    private RecyclerView recyclerView;
    private BarberService service;
    private SharedPreferences mPrefs;

    public FreeHourClick(Activity activity, RecyclerView recyclerView, BarberService service) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.service = service;
    }

    @Override
    public void onItemClick(View view, int position) {
        FreeHoursAdapter adapter = (FreeHoursAdapter) recyclerView.getAdapter();

        final Time time = adapter.getItem(position);

        if (!time.GetAvailability()) {
            Toast.makeText(activity, R.string.unavailable_time, Toast.LENGTH_SHORT).show();
        } else {
            boolean not_enough_time = false;
            int total_time_spaces = 0;

            try {
                int time_spaces_for_service = ((int) service.getDuration()) / BarberFreeHoursActivity.MINUTES_PER_TIME_SPACE;
                int temporal_position = position;
                while (time_spaces_for_service >= 0) {
                    Time current_time = adapter.getItem(temporal_position);
                    if (!current_time.GetAvailability()) {
                        not_enough_time = true;
                        break;
                    }
                    temporal_position++;
                    total_time_spaces++;
                    time_spaces_for_service--;
                }
            } catch (Exception ex) {
                not_enough_time = true;
            }

            if (not_enough_time) {
                AlertDialog alert = new AlertDialog.Builder(this.activity).create();

                alert.setTitle(activity.getString(R.string.free_hour_click_not_enough_time_title));

                alert.setMessage(String.format(activity.getString(R.string.free_hour_click_not_enough_time_dialog),
                        service.getName(), String.valueOf((int) service.getDuration()),
                        String.valueOf(total_time_spaces * BarberFreeHoursActivity.MINUTES_PER_TIME_SPACE)));

                alert.setButton(DialogInterface.BUTTON_POSITIVE, activity.getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(activity, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("user", "");
                        activity.startActivity(intent);
                    }
                });

                alert.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            } else {
                AlertDialog alert = new AlertDialog.Builder(this.activity).create();

                alert.setTitle(activity.getString(R.string.free_hour_click_title));

                String minutes = String.valueOf(time.getMinutes());
                if (minutes.equals("0")) {
                    minutes = "00";
                }

                alert.setMessage(String.format(activity.getString(R.string.free_hour_click_dialog),
                        service.getName(), time.getYear() + "-" + time.getMonth() + "-" +
                                time.getDay(), time.getHour() + ":" + minutes));

                alert.setButton(DialogInterface.BUTTON_POSITIVE, activity.getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String db_format_time = time.getYear() + "-" + time.getMonth() + "-" + time.getDay() + " " + time.getHour() + ":" +
                                time.getMinutes();

                        mPrefs = activity.getSharedPreferences("USER", MODE_PRIVATE);
                        Gson gson = new Gson();
                        String json = mPrefs.getString("user", "");
                        final Client client = gson.fromJson(json, Client.class);

                        APIController.getInstance().getPromotionByService(
                                client.getToken(),
                                String.valueOf(service.getBarberShopId()),
                                String.valueOf(service.getId()))
                                .addOnCompleteListener(new OnCompleteListener<Promotion>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Promotion> task) {
                                        Promotion promotion = task.getResult();
                                        APIController.getInstance().createAppointment(client.getToken(),
                                                new Appointment(-1, client.getId(), service.getBarberShopId(),
                                                service.getId(), promotion.getId(), db_format_time))
                                                .addOnCompleteListener(new OnCompleteListener<Integer>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Integer> task) {
                                                Intent intent = new Intent(activity, HomeActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                intent.putExtra("user", "User");
                                                activity.startActivity(intent);
                                            }
                                        });
                                    }
                                });

                    }
                });

                alert.setButton(DialogInterface.BUTTON_NEGATIVE, activity.getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        }
    }
}
