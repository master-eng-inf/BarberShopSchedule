package com.udl.bss.barbershopschedule.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alex on 08/11/2017.
 */

public class BarberService implements Parcelable {

    private int service_id;
    private int barber_shop_id;
    private String service_name;
    private double service_price;
    private double service_duration;

    public BarberService(int service_id, int barber_shop_id, String service_name, double service_price, double service_duration) {
        this.service_id = service_id;
        this.barber_shop_id = barber_shop_id;
        this.service_name = service_name;
        this.service_price = service_price;
        this.service_duration = service_duration;
    }

    private BarberService(Parcel in) {
        this.service_id = in.readInt();
        this.barber_shop_id = in.readInt();
        this.service_name = in.readString();
        this.service_price = in.readDouble();
        this.service_duration = in.readDouble();
    }

    public static final Creator<BarberService> CREATOR = new Creator<BarberService>() {
        @Override
        public BarberService createFromParcel(Parcel source) {
            return new BarberService(source);
        }

        @Override
        public BarberService[] newArray(int size) {
            return new BarberService[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(service_id);
        dest.writeInt(barber_shop_id);
        dest.writeString(service_name);
        dest.writeDouble(service_price);
        dest.writeDouble(service_duration);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String Get_Name() {
        return this.service_name;
    }

    public double Get_Price() {
        return this.service_price;
    }

    public int Get_Id() {
        return this.service_id;
    }

    public double Get_Duration() {
        return this.service_duration;
    }

    public int Get_BarberShopId() {
        return this.barber_shop_id;
    }

    public void Set_Name(String new_name) {
        this.service_name = new_name;
    }

    public void Set_Price(double new_price) {
        this.service_price = new_price;
    }

    public void Set_Duration(double new_duration) {
        this.service_duration = new_duration;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + service_id +
                ", barber_shop_id='" + barber_shop_id + '\'' +
                ", service_name='" + service_name + '\'' +
                ", service_price='" + service_price + '\'' +
                ", service_duration='" + service_duration + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BarberService barber_service = (BarberService) o;

        if (service_id != barber_service.service_id) return false;
        if (barber_shop_id != barber_service.barber_shop_id) return false;
        if (service_price != barber_service.service_price) return false;
        if (service_name != null ? !service_name.equals(barber_service.service_name) : barber_service.service_name != null) return false;
        return barber_shop_id != barber_service.barber_shop_id;
    }

    @Override
    public int hashCode() {
        int result = service_id;
        result = 31 * result + (barber_shop_id);
        result = 31 * result + (service_name != null ? service_name.hashCode() : 0);
        result = 31 * result + ((int)service_price);
        result = 31 * result + ((int)service_duration);
        return result;
    }
}

