package com.udl.bss.barbershopschedule.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.udl.bss.barbershopschedule.BarberFreeHoursActivity;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.domain.Barber;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class BarberDetailScheduleFragment extends Fragment {

    private static final String BARBER_SHOP = "barber_shop";
    private Barber barber;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barber_detail_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CalendarView calendar = (CalendarView) view.findViewById(R.id.client_schedule);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                if (year >= calendar.get(Calendar.YEAR) && month >= calendar.get(Calendar.MONTH) && dayOfMonth >= calendar.get(Calendar.DAY_OF_MONTH)) {
                    Intent intent = new Intent(getContext(), BarberFreeHoursActivity.class);
                    intent.putExtra("barber", barber);

                    startActivity(intent);
                }
                else {
                    Toast.makeText(getContext(), R.string.unavailable_date, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
