package com.udl.bss.barbershopschedule.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alex on 23/11/2017.
 */

public class Review {
    private int barber_shop_id;
    private int client_id;
    private String description;
    private double mark;
    private Date date;

    public Review(int client_id, int barber_shop_id, String description, double mark, String date) {
        this.client_id = client_id;
        this.barber_shop_id = barber_shop_id;
        this.description = description;
        this.mark = mark;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            this.date = format.parse(date);
        } catch (ParseException e) {
            this.date = null;
        }
    }

    public int getClientId() {
        return this.client_id;
    }

    public int getBarberShopId() {
        return this.barber_shop_id;}

    public String getDescription() {
        return this.description;
    }

    public double getMark() {
        return this.mark;
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return dateFormat.format(this.date);
    }

    public void setDescription(String new_description) {
        this.description = new_description;
    }

    public void setMark(double new_mark) {
        this.mark = new_mark;
    }

    public void setDate(Date new_date) {
        this.date = new_date;
    }
}
