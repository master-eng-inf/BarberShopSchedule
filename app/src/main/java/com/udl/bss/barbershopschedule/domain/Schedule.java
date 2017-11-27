package com.udl.bss.barbershopschedule.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 25/11/2017.
 */

public class Schedule {
    private int barber_shop_id;
    private int day_of_week;
    private Date oppening_1;
    private Date closing_1;
    private Date oppening_2;
    private Date closing_2;
    private int appointments_at_same_time;

    public Schedule(int barber_shop_id, int day_of_week, String oppening_1, String closing_1,
                    String oppening_2, String closing_2, int appointments_at_same_time) {
        this.barber_shop_id = barber_shop_id;
        this.day_of_week = day_of_week;
        this.appointments_at_same_time = appointments_at_same_time;

        SimpleDateFormat format = new SimpleDateFormat("HH:MM");
        try {
            this.oppening_1 = format.parse(oppening_1);
            this.oppening_2 = format.parse(oppening_2);
            this.closing_1 = format.parse(closing_1);
            this.closing_2 = format.parse(closing_2);
        } catch (ParseException e) {
            this.oppening_1 = null;
            this.oppening_2 = null;
            this.closing_1 = null;
            this.closing_2 = null;
        }
    }

    public Schedule(int barber_shop_id, int day_of_week, Date oppening_1, Date closing_1,
                    Date oppening_2, Date closing_2, int appointments_at_same_time) {
        this.barber_shop_id = barber_shop_id;
        this.day_of_week = day_of_week;
        this.appointments_at_same_time = appointments_at_same_time;
        this.oppening_1 = oppening_1;
        this.closing_1 = closing_1;
        this.oppening_2 = oppening_2;
        this.closing_2 = closing_2;
    }

    public int GetBarber_shop_id() {
        return this.barber_shop_id;
    }

    public void SetBarber_shop_id(int new_barber_shop_id) {
        this.barber_shop_id = new_barber_shop_id;
    }

    public int GetDay_of_week() {
        return this.day_of_week;
    }

    public void SetDay_of_week(int new_day_of_week) {
        this.day_of_week = new_day_of_week;
    }

    public int GetAppointments_at_same_time() {
        return this.appointments_at_same_time;
    }

    public void SetAppointments_at_same_time(int new_appointments_at_same_time) {
        this.appointments_at_same_time = appointments_at_same_time;
    }

    public String GetOppening1() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        return dateFormat.format(this.oppening_1);
    }

    public void SetOppening1(Date new_oppening1) {
        this.oppening_1 = new_oppening1;
    }

    public String GetOppening2() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        return dateFormat.format(this.oppening_2);
    }

    public void SetOppening2(Date new_oppening2) {
        this.oppening_2 = new_oppening2;
    }

    public String GetClosing1() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        return dateFormat.format(this.closing_1);
    }

    public void SetClosing1(Date new_closing1) {
        this.closing_1 = new_closing1;
    }

    public String GetClosing2() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        return dateFormat.format(this.closing_2);
    }

    public void SetClosing2(Date new_closing2) {
        this.closing_2 = new_closing2;
    }
}
