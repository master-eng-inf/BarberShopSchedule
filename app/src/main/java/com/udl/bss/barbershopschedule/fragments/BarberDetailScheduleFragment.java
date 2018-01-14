package com.udl.bss.barbershopschedule.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.BarberServicePricesActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.domain.Schedule;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class BarberDetailScheduleFragment extends Fragment {

    private static final String BARBER_SHOP = "barber_shop";
    private Barber barber;
    private SharedPreferences mPrefs;
    private Client client;

    public static BarberDetailScheduleFragment newInstance(Barber param1) {
        BarberDetailScheduleFragment fragment = new BarberDetailScheduleFragment();
        Bundle args = new Bundle();
        args.putParcelable(BARBER_SHOP, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.barber = getArguments().getParcelable(BARBER_SHOP);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barber_detail_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) mPrefs = getActivity().getSharedPreferences("USER", Activity.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("user", "");
        client = gson.fromJson(json, Client.class);

        CalendarView calendar = view.findViewById(R.id.client_schedule);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, final int year, final int month, final int dayOfMonth) {
                final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    calendar.setTime(format.parse(String.valueOf(year) + "-" + String.valueOf(month+1) + "-" + String.valueOf(dayOfMonth)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 2;

                APIController.getInstance().getBarberShopScheduleByDay(
                        client.getToken(),
                        String.valueOf(barber.getId()),
                        String.valueOf(day_of_week))
                        .addOnCompleteListener(new OnCompleteListener<Schedule>() {
                    @Override
                    public void onComplete(@NonNull Task<Schedule> task) {

                        if (year >= calendar.get(Calendar.YEAR) &&
                                month >= calendar.get(Calendar.MONTH) &&
                                dayOfMonth >= calendar.get(Calendar.DAY_OF_MONTH) &&
                                task.getResult() != null) {
                            Intent intent = new Intent(getContext(), BarberServicePricesActivity.class);
                            intent.putExtra("barber_shop_id", barber.getId());
                            intent.putExtra("date", String.valueOf(year) + "-" + String.valueOf(month+1) + "-" + String.valueOf(dayOfMonth));

                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), R.string.unavailable_date, Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });
    }
}
