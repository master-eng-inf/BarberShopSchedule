package com.udl.bss.barbershopschedule.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.date = format.parse(date);
        } catch (ParseException e) {
            this.date = null;
        }
    }

    public int GetClientId() {
        return this.client_id;
    }

    public int GetBarberShopId() {
        return this.barber_shop_id;}

    public String GetDescription() {
        return this.description;
    }

    public double GetMark() {
        return this.mark;
    }

    public String GetDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(this.date);
    }

    public void SetDescription(String new_description) {
        this.description = new_description;
    }

    public void SetMark(double new_mark) {
        this.mark = new_mark;
    }

    public void SetDate(Date new_date) {
        this.date = new_date;
    }
}
