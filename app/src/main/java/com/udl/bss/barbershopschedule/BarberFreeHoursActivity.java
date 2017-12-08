package com.udl.bss.barbershopschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;

import com.udl.bss.barbershopschedule.adapters.FreeHoursAdapter;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Schedule;
import com.udl.bss.barbershopschedule.domain.Time;
import com.udl.bss.barbershopschedule.listeners.FreeHourClick;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class BarberFreeHoursActivity extends AppCompatActivity {

    private BarberService barberService;
    private RecyclerView freeHoursRecycleView;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_free_hours);

        barberService = getIntent().getParcelableExtra("service");
        date = getIntent().getParcelableExtra("date");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.freeHoursRecycleView = findViewById(R.id.free_hours);

        if (this.freeHoursRecycleView != null) {
            this.freeHoursRecycleView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            this.freeHoursRecycleView.setLayoutManager(llm);

            SetFreeHours();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    private void SetFreeHours() {
        BLL instance = new BLL(this);
        ArrayList<Appointment> appointments_for_current_day = instance.Get_BarberShopAppointmentsForSpecificDate(this.barberService.Get_BarberShopId(), this.date);
        List<Time> freeHoursList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.date);
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        Schedule schedule = instance.Get_BarberShopScheduleForSpecificDay(this.barberService.Get_BarberShopId(), day_of_week);

        Pair<Integer, Integer> oppening1 = ParseTime(schedule.GetOppening1());
        Pair<Integer, Integer> closing1 = ParseTime(schedule.GetClosing1());
        Pair<Integer, Integer> oppening2 = ParseTime(schedule.GetOppening2());
        Pair<Integer, Integer> closing2 = ParseTime(schedule.GetClosing2());

        //Morning schedule
        int morning_total_minutes = GetTimeDifference(oppening1, closing1);
        while(morning_total_minutes > 0)
        {

        }

        //Afternoon schedule
        int afternoon_total_minutes = GetTimeDifference(oppening2, closing2);
        while(afternoon_total_minutes > 0)
        {

        }

        Iterator<Appointment> iterator = appointments_for_current_day.iterator();

        while (iterator.hasNext()) {
            Appointment current_appointment = iterator.next();

        }

        freeHoursList.add(new Time(2017, 11, 11, 10, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 10, 30, false));
        freeHoursList.add(new Time(2017, 11, 11, 11, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 11, 30, true));
        freeHoursList.add(new Time(2017, 11, 11, 12, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 12, 30, true));
        freeHoursList.add(new Time(2017, 11, 11, 13, 00, false));
        freeHoursList.add(new Time(2017, 11, 11, 15, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 15, 30, true));
        freeHoursList.add(new Time(2017, 11, 11, 16, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 16, 30, true));
        freeHoursList.add(new Time(2017, 11, 11, 17, 00, false));
        freeHoursList.add(new Time(2017, 11, 11, 17, 30, false));
        freeHoursList.add(new Time(2017, 11, 11, 18, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 18, 30, true));
        freeHoursList.add(new Time(2017, 11, 11, 19, 00, true));
        freeHoursList.add(new Time(2017, 11, 11, 19, 30, false));

        FreeHoursAdapter adapter = new FreeHoursAdapter(freeHoursList, new FreeHourClick(this, this.freeHoursRecycleView, this.barberService), this);
        this.freeHoursRecycleView.setAdapter(adapter);
    }

    private Pair<Integer, Integer> ParseTime(String string_hour) {
        String[] separated = string_hour.split(":");
        return new Pair<>(Integer.parseInt(separated[0]), Integer.parseInt(separated[1]));
    }

    private Pair<Integer, Integer> AddMinutes(Pair<Integer, Integer> time, int minutes_to_add) {
        int hour = time.first;
        int minutes = time.second;

        int new_hour = hour;
        int new_minutes = minutes + minutes_to_add;

        if (new_minutes >= 60) {
            new_minutes = new_minutes - 60;
            new_hour = new_hour + 1;
        }

        return new Pair<>(new_hour, new_minutes);
    }

    private int GetTimeDifference(Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        if (to.first >= from.first && to.second >= from.second) {
            int hours_difference = to.first - from.first;
            int minutes_difference = to.second - from.second;

            return hours_difference * 60 + minutes_difference;
        }

        return -1;
    }
}
