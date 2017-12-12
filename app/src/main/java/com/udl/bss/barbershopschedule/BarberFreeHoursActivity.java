package com.udl.bss.barbershopschedule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.View;

import com.udl.bss.barbershopschedule.adapters.FreeHoursAdapter;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Schedule;
import com.udl.bss.barbershopschedule.domain.Time;
import com.udl.bss.barbershopschedule.listeners.FreeHourClick;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class BarberFreeHoursActivity extends AppCompatActivity {

    private BarberService barberService;
    private RecyclerView freeHoursRecycleView;
    private Calendar date;
    //TODO Establish minutes to add
    public static final int MINUTES_PER_TIME_SPACE = 5;

    private Pair<Integer, Integer> oppening1;
    private Pair<Integer, Integer> closing1;
    private Pair<Integer, Integer> oppening2;
    private Pair<Integer, Integer> closing2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber_free_hours);

        barberService = getIntent().getParcelableExtra("service");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            this.date = Calendar.getInstance();
            this.date.setTime(format.parse(getIntent().getStringExtra("date")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

        int day_of_week = date.get(Calendar.DAY_OF_WEEK) - 2;

        Schedule schedule = instance.Get_BarberShopScheduleForSpecificDay(this.barberService.Get_BarberShopId(), day_of_week);

        if (schedule != null) {
            oppening1 = ParseTime(schedule.GetOppening1());
            closing1 = ParseTime(schedule.GetClosing1());
            oppening2 = ParseTime(schedule.GetOppening2());
            closing2 = ParseTime(schedule.GetClosing2());

            if (oppening1 != null && oppening2 != null && closing1 != null && closing2 != null) {
                //Morning schedule
                Pair<Integer, Integer> time_count1 = oppening1;
                int morning_total_minutes = GetTimeDifference(oppening1, closing1);
                while (morning_total_minutes >= 0) {
                    freeHoursList.add(new Time(this.date.get(Calendar.YEAR), this.date.get(Calendar.MONTH) + 1,
                            this.date.get(Calendar.DAY_OF_MONTH), time_count1.first, time_count1.second, true));

                    time_count1 = AddMinutes(time_count1, MINUTES_PER_TIME_SPACE);

                    morning_total_minutes -= MINUTES_PER_TIME_SPACE;
                }

                //Afternoon schedule
                Pair<Integer, Integer> time_count2 = oppening2;
                int afternoon_total_minutes = GetTimeDifference(oppening2, closing2);
                while (afternoon_total_minutes >= 0) {
                    freeHoursList.add(new Time(this.date.get(Calendar.YEAR), this.date.get(Calendar.MONTH) + 1,
                            this.date.get(Calendar.DAY_OF_MONTH), time_count2.first, time_count2.second, true));

                    time_count2 = AddMinutes(time_count2, MINUTES_PER_TIME_SPACE);

                    afternoon_total_minutes -= MINUTES_PER_TIME_SPACE;
                }
            } else if (oppening1 != null && oppening2 == null && closing1 == null && closing2 != null) {
                //No rest, full day schedule
                Pair<Integer, Integer> time_count1 = oppening1;
                int morning_total_minutes = GetTimeDifference(oppening1, closing2);
                while (morning_total_minutes >= 0) {
                    freeHoursList.add(new Time(this.date.get(Calendar.YEAR), this.date.get(Calendar.MONTH) + 1,
                            this.date.get(Calendar.DAY_OF_MONTH), time_count1.first, time_count1.second, true));

                    time_count1 = AddMinutes(time_count1, MINUTES_PER_TIME_SPACE);

                    morning_total_minutes -= MINUTES_PER_TIME_SPACE;
                }
            }

            if (freeHoursList.size() > 0) {
                Iterator<Appointment> appointments_iterator = appointments_for_current_day.iterator();

                while (appointments_iterator.hasNext()) {
                    Appointment current_appointment = appointments_iterator.next();
                    BarberService current_service = instance.Get_BarberShopService(current_appointment.getService_id());

                    try {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Calendar appointment_date = Calendar.getInstance();

                        appointment_date.setTime(format.parse(current_appointment.getDate()));

                        ArrayList<Integer> index = GetListIndexForTime(new Pair<>(appointment_date.get(Calendar.HOUR_OF_DAY), appointment_date.get(Calendar.MINUTE)), (int) current_service.Get_Duration());

                        Iterator index_iterator = index.iterator();
                        while (index_iterator.hasNext()) {
                            int current_index = (int) index_iterator.next();

                            Time current_time = freeHoursList.get(current_index);
                            current_time.SetAvailability(false);

                            freeHoursList.set(current_index, current_time);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                FreeHoursAdapter adapter = new FreeHoursAdapter(freeHoursList, new FreeHourClick(this, this.freeHoursRecycleView, this.barberService), this);
                this.freeHoursRecycleView.setAdapter(adapter);
            }
        }
    }

    private Pair<Integer, Integer> ParseTime(String string_hour) {
        try {
            String[] separated = string_hour.split(":");
            return new Pair<>(Integer.parseInt(separated[0]), Integer.parseInt(separated[1]));
        } catch (Exception ex) {
            return null;
        }
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

        if (from == null || to == null) {
            return -1;
        }

        if (to.first >= from.first && to.second >= from.second) {
            int hours_difference = to.first - from.first;
            int minutes_difference = to.second - from.second;

            return hours_difference * 60 + minutes_difference;
        }

        return -1;
    }

    private ArrayList<Integer> GetListIndexForTime(Pair<Integer, Integer> appointment_time, int service_duration) {
        ArrayList<Integer> time_spaces = new ArrayList<>();

        int time_to_appointment = GetTimeDifference(oppening1, appointment_time);

        //TODO what if division is not exact
        int time_spaces_until_appointment = time_to_appointment / MINUTES_PER_TIME_SPACE;

        //TODO what if division is not exact
        int time_spaces_for_appointment = service_duration / MINUTES_PER_TIME_SPACE;

        if (closing1 != null && oppening2 != null) {
            int time_to_closing1 = GetTimeDifference(oppening1, closing1);

            if (time_to_appointment > time_to_closing1) {
                int rest_time = GetTimeDifference(closing1, oppening2);
                int rest_time_spaces = (rest_time / MINUTES_PER_TIME_SPACE) - 1;
                time_spaces_until_appointment -= rest_time_spaces;
            }
        }

        while (time_spaces_for_appointment > 0) {
            time_spaces.add(time_spaces_until_appointment++);
            time_spaces_for_appointment--;
        }

        return time_spaces;
    }
}
