package com.udl.bss.barbershopschedule.domain;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alex on 23/11/2017.
 */

public class Review {
    private String description;
    private int mark;
    private Time date;

    public Review(String description, int mark, Time date)
    {
        this.description = description;
        this.mark = mark;
        this.date = date;
    }

    public String GetDescription()
    {
        return this.description;
    }

    public int GetMark()
    {
        return this.mark;
    }

    public Time GetDate()
    {
        return this.date;
    }

    public void SetDescription(String new_description)
    {
        this.description = new_description;
    }

    public void SetMark(int new_mark)
    {
        this.mark = new_mark;
    }

    public void SetDate(Time new_date)
    {
        this.date = new_date;
    }
}
