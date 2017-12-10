package com.udl.bss.barbershopschedule.listeners;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.HomeActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.FreeHoursAdapter;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.domain.Time;

/**
 * Created by Alex on 10/11/2017.
 */

public class FreeHourClick implements OnItemClickListener {

    private Activity activity;
    private RecyclerView recyclerView;
    private BarberService service;

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
            AlertDialog alert = new AlertDialog.Builder(this.activity).create();

            alert.setTitle(activity.getString(R.string.free_hour_click_title));

            String minutes = String.valueOf(time.getMinutes());
            if (minutes.equals("0")) {
                minutes = "00";
            }

            alert.setMessage(String.format(activity.getString(R.string.free_hour_click_dialog),
                    service.Get_Name(), time.getYear() + "-" + time.getMonth() + "-" +
                            time.getDay(), time.getHour() + ":" + minutes));

            alert.setButton(DialogInterface.BUTTON_POSITIVE, activity.getString(R.string.accept_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String db_format_time = time.getYear() + "-" + time.getMonth() + "-" + time.getDay() + " " + time.getHour() + ":" +
                            time.getMinutes();

                    BLL instance = new BLL(activity);

                    Promotion promotion = instance.Get_BarberShopPromotionForService(service.Get_BarberShopId(), service.Get_Id());

                    //TODO Change Client Id
                    instance.Insert_Appointment(new Appointment(-1, 0, service.Get_BarberShopId(),
                            service.Get_Id(), promotion == null ? -1 : promotion.getId(), db_format_time));

                    /*
                    Intent intent = new Intent(activity, HomeActivity.class);
                    activity.startActivity(intent);

                    activity.finish();
                    */
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
