package com.udl.bss.barbershopschedule.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Updated by Julio on 23/11/2017.
 */

public class Service implements Parcelable{

    private int id;
    private int barber_id;
    private String name;
    private float price;
    private int duration;

    public Service(int id, String name, int barber_id, float price, int duration) {
        this.id = id;
        this.barber_id = barber_id;
        this.name = name;
        this.price = price;
        this.duration = duration;
    }

    private Service(Parcel in){
        this.id = in.readInt();
        this.barber_id = in.readInt();
        this.name = in.readString();
        this.price = in.readFloat();
        this.duration = in.readInt();
    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    public Service(int id, int barber_id, String name, double price, int duration) {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(barber_id);
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeInt(duration);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    /**********Getters**********/
    public int getId() {
        return this.id;
    }

    private int getBarberId() {
        return this.barber_id;
    }

    public String getName() {
        return this.name;
    }

    public float getPrice() {
        return this.price;
    }

    public int getDuration() {
        return this.duration;
    }


    /**********Setters**********/
    public void setId(int new_id) {
        this.id = new_id;
    }

    private void setBarberId(int new_barber_id) {
        this.barber_id = barber_id;
    }

    public void setName(String new_name) {
        this.name = new_name;
    }

    public void setPrice(float new_price) {
        this.price = new_price;
    }

    public void setDuration(int new_duration) {
        this.duration = new_duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service1 = (Service) o;

        if (id != service1.id) return false;
        if (barber_id != service1.barber_id) return false;
        if (Float.compare(service1.price, price) != 0) return false;
        if (duration != service1.duration) return false;
        return name != null ? !name.equals(service1.name) : service1.name != null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + barber_id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + duration;
        return result;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", barber_id='" + barber_id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }
}

