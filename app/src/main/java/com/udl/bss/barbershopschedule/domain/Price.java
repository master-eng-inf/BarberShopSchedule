package com.udl.bss.barbershopschedule.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Price implements Parcelable{

    private int id;
    private String barberShopName;
    private String service;
    private float price;

    public Price(int id, String barberShopName, String service, float price) {
        this.id = id;
        this.barberShopName = barberShopName;
        this.service = service;
        this.price = price;
    }


    private Price(Parcel in) {
        this.id = in.readInt();
        this.barberShopName = in.readString();
        this.service = in.readString();
        this.price = in.readFloat();
    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel in) {
            return new Price(in);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(barberShopName);
        dest.writeString(service);
        dest.writeFloat(price);
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price1 = (Price) o;

        if (id != price1.id) return false;
        if (Float.compare(price1.price, price) != 0) return false;
        if (barberShopName != null ? !barberShopName.equals(price1.barberShopName) : price1.barberShopName != null)
            return false;
        return service != null ? service.equals(price1.service) : price1.service == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (barberShopName != null ? barberShopName.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Prices{" +
                "id=" + id +
                ", barberShopName='" + barberShopName + '\'' +
                ", service='" + service + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
