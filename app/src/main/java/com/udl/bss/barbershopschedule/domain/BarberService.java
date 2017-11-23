package com.udl.bss.barbershopschedule.domain;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Julio on 23/11/2017.
 */

public class BarberService implements Parcelable{

    private int service_id;
    //private int service_barber_id;
    private String service_name;
    private float service_price;
    private int service_duration;

    public BarberService(int service_id, String service_name, float service_price, int service_duration) {
        this.service_id = service_id;
        //this.service_barber_id = service_barber_id;
        this.service_name = service_name;
        this.service_price = service_price;
        this.service_duration = service_duration;
    }

    private BarberService(Parcel in){
        this.service_id = in.readInt();
        //this.service_barber_id = in.readInt();
        this.service_name = in.readString();
        this.service_price = in.readFloat();
        this.service_duration = in.readInt();
    }

    public static final Creator<BarberService> CREATOR = new Creator<BarberService>() {
        @Override
        public BarberService createFromParcel(Parcel in) {
            return new BarberService(in);
        }

        @Override
        public BarberService[] newArray(int size) {
            return new BarberService[size];
        }
    };

    public BarberService(int id, String name, double price, int duration, Bitmap bitmap) {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(service_id);
        //dest.writeInt(service_barber_id);
        dest.writeString(service_name);
        dest.writeFloat(service_price);
        dest.writeInt(service_duration);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    /**********Getters**********/
    public int GetId() {
        return this.service_id;
    }

    /*private int GetBarberId() {
        return this.service_barber_id;
    }*/

    public String GetName() {
        return this.service_name;
    }

    public float GetPrice() {
        return this.service_price;
    }

    public int GetDuration() {
        return this.service_duration;
    }


    /**********Setters**********/
    public void SetId(int new_id) {
        this.service_id = new_id;
    }

    /*private void SetBarberId(int new_barberId) {
        this.service_barber_id = new_barberId;
    }*/

    public void SetName(String new_name) {
        this.service_name = new_name;
    }

    public void SetPrice(float new_price) {
        this.service_price = new_price;
    }

    public void SetDuration(int new_duration) {
        this.service_duration = new_duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BarberService barberService1 = (BarberService) o;

        if (service_id != barberService1.service_id) return false;
        /*if (service_barber_id != barberService1.service_barber_id) return false;*/
        if (Float.compare(barberService1.service_price, service_price) != 0) return false;
        if (service_duration != barberService1.service_duration) return false;
        return service_name != null ? !service_name.equals(barberService1.service_name) : barberService1.service_name != null;
    }

    @Override
    public int hashCode() {
        int result = service_id;
        /*result = 31 * result + service_barber_id;*/
        result = 31 * result + (service_name != null ? service_name.hashCode() : 0);
        result = 31 * result + (service_price != +0.0f ? Float.floatToIntBits(service_price) : 0);
        result = 31 * result + service_duration;
        return result;
    }

    @Override
    public String toString() {
        return "Prices{" +
                "id=" + service_id +
                /*", barber_id='" + service_barber_id + '\'' +*/
                ", name='" + service_name + '\'' +
                ", price='" + service_price + '\'' +
                ", duration='" + service_duration + '\'' +
                '}';
    }
}

