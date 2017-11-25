package com.udl.bss.barbershopschedule.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment implements Parcelable {
    private int id;
    private int client_id;
    private int barber_shop_id;
    private int service_id;
    private int promotion_id;
    private Date date;

    public Appointment(int id, int client_id, int barber_shop_id, int service_id, int promotion_id, String date) {
        this.id = id;
        this.client_id = client_id;
        this.barber_shop_id = barber_shop_id;
        this.service_id = service_id;
        this.promotion_id = promotion_id;

        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        try {
            this.date = format.parse(date);
            System.out.println(date);
        } catch (ParseException e) {
            this.date = null;
        }
    }

    public Appointment(int id, int client_id, int barber_shop_id, int service_id, int promotion_id, Date date) {
        this.id = id;
        this.client_id = client_id;
        this.barber_shop_id = barber_shop_id;
        this.service_id = service_id;
        this.promotion_id = promotion_id;
        this.date = date;
    }

    private Appointment(Parcel in) {
        this.id = in.readInt();
        this.client_id = in.readInt();
        this.barber_shop_id = in.readInt();
        this.service_id = in.readInt();
        this.promotion_id = in.readInt();
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
        dest.writeInt(client_id);
        dest.writeInt(barber_shop_id);
        dest.writeInt(service_id);
        dest.writeInt(promotion_id);
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

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int new_client_id) {
        this.client_id = new_client_id;
    }

    public int getBarber_shop_id() {
        return barber_shop_id;
    }

    public void setBarber_shop_id(int new_barber_shop_id) {
        this.barber_shop_id = new_barber_shop_id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int new_service_id) {
        this.service_id = new_service_id;
    }

    public int getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(int new_promotion_id) {
        this.promotion_id = new_promotion_id;
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS.SSS");
        return dateFormat.format(this.date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", client_id='" + client_id + '\'' +
                ", barber_shop_id='" + barber_shop_id + '\'' +
                ", service_id='" + service_id + '\'' +
                ", promotion_id='" + promotion_id + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Appointment that = (Appointment) o;

        if (id != that.id) return false;
        if (client_id != that.client_id) return false;
        if (service_id != that.service_id) return false;
        if (barber_shop_id != that.barber_shop_id) return false;
        if (promotion_id != that.promotion_id) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (client_id);
        result = 31 * result + (barber_shop_id);
        result = 31 * result + (service_id);
        result = 31 * result + (promotion_id);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
