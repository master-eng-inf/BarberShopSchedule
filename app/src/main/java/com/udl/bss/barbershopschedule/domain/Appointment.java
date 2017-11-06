package com.udl.bss.barbershopschedule.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Appointment implements Parcelable {
    private int id;
    private String barberShopName;
    private String service;
    private Date date;

    public Appointment(int id, String barberShopName, String service, Date date) {
        this.id = id;
        this.barberShopName = barberShopName;
        this.service = service;
        this.date = date;
    }

    private Appointment(Parcel in) {
        this.id = in.readInt();
        this.barberShopName = in.readString();
        this.service = in.readString();
        this.date = new Date(in.readLong());
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(barberShopName);
        dest.writeString(service);
        dest.writeLong(date.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarberShopName() {
        return barberShopName;
    }

    public void setBarberShopName(String barberShopName) {
        this.barberShopName = barberShopName;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", barberShopName='" + barberShopName + '\'' +
                ", service='" + service + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Appointment that = (Appointment) o;

        if (id != that.id) return false;
        if (barberShopName != null ? !barberShopName.equals(that.barberShopName) : that.barberShopName != null)
            return false;
        if (service != null ? !service.equals(that.service) : that.service != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (barberShopName != null ? barberShopName.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
