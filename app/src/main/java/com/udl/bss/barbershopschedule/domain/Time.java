package com.udl.bss.barbershopschedule.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alex on 11/11/2017.
 */

public class Time implements Parcelable {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minutes;
    private boolean available;

    public Time(int year, int month, int day, int hour, int minutes, boolean available) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minutes = minutes;
        this.available = available;
    }

    public int GetYear() {
        return this.year;
    }

    public int GetMonth() {
        return this.month;
    }

    public int GetDay() {
        return this.day;
    }

    public int GetHour() {
        return this.hour;
    }

    public int GetMinutes() {
        return this.minutes;
    }

    public boolean GetAvailability() {
        return this.available;
    }

    public void SetMonth(int new_month) {
        this.month = new_month;
    }

    public void SetDay(int new_day) {
        this.day = new_day;
    }

    public void SetHour(int new_hour) {
        this.hour = new_hour;
    }

    public void SetMinutes(int new_minutes) {
        this.minutes = new_minutes;
    }

    public void SetAvailability(boolean new_availability) {
        this.available = new_availability;
    }

    private Time(Parcel in) {
        this.year = in.readInt();
        this.month = in.readInt();
        this.day = in.readInt();
        this.hour = in.readInt();
        this.minutes = in.readInt();
        //this.available = in.bool();
    }

    public static final Creator<Time> CREATOR = new Creator<Time>() {
        @Override
        public Time createFromParcel(Parcel in) {
            return new Time(in);
        }

        @Override
        public Time[] newArray(int size) {
            return new Time[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);
        dest.writeInt(hour);
        dest.writeInt(minutes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return this.minutes;
    }

    public void setMinutes(int minutes) {
        this.day = minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time time = (Time) o;

        if (year != time.year && month != time.month && day != time.day && hour != time.hour && minutes != time.minutes) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = year;
        result = 31 * result + month;
        result = 31 * result + day;
        result = 31 * result + hour;
        result = 31 * result + minutes;
        return result;
    }

    @Override
    public String toString() {
        return "Time{" +
                "year=" + year +
                ", month='" + month + '\'' +
                ", day='" + day + '\'' +
                ", hour='" + hour + '\'' +
                ", minutes='" + minutes + '\'' +
                '}';
    }
}
