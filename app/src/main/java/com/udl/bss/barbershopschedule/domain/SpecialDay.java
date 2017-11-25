package com.udl.bss.barbershopschedule.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 25/11/2017.
 */

public class SpecialDay {
    private int barber_shop_id;
    private Date date;
    private int type_of_day;

    public SpecialDay(int barber_shop_id, String date, int type_of_day) {
        this.barber_shop_id = barber_shop_id;
        this.type_of_day = type_of_day;

        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        try {
            this.date = format.parse(date);
            System.out.println(date);
        } catch (ParseException e) {
            this.date = null;
        }
    }

    public SpecialDay(int barber_shop_id, Date date, int type_of_day) {
        this.barber_shop_id = barber_shop_id;
        this.date = date;
        this.type_of_day = type_of_day;
    }

    public int GetBarber_shop_id() {
        return this.barber_shop_id;
    }

    public void SetBarber_shop_id(int new_barber_shop_id) {
        this.barber_shop_id = new_barber_shop_id;
    }

    public String GetDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        return dateFormat.format(this.date);
    }

    public void SetDate(Date new_date) {
        this.date = new_date;
    }

    public int Get_day_type() {
        return this.type_of_day;
    }

    public void Set_day_type(int type_of_day) {
        this.type_of_day = type_of_day;
    }
}
