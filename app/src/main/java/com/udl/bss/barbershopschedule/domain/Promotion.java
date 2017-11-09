package com.udl.bss.barbershopschedule.domain;


import android.os.Parcel;
import android.os.Parcelable;

public class Promotion implements Parcelable{

    private int id;
    private String barberShopName;
    private String service;
    private String description;

    public Promotion(int id, String barberShopName, String service, String description) {
        this.id = id;
        this.barberShopName = barberShopName;
        this.service = service;
        this.description = description;
    }

    private Promotion(Parcel in) {
        this.id = in.readInt();
        this.barberShopName = in.readString();
        this.service = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Promotion> CREATOR = new Parcelable.Creator<Promotion>() {
        @Override
        public Promotion createFromParcel(Parcel in) {
            return new Promotion(in);
        }

        @Override
        public Promotion[] newArray(int size) {
            return new Promotion[size];
        }
    };

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(barberShopName);
        dest.writeString(service);
        dest.writeString(description);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Promotion promotion = (Promotion) o;

        if (id != promotion.id) return false;
        if (barberShopName != null ? !barberShopName.equals(promotion.barberShopName) : promotion.barberShopName != null)
            return false;
        if (service != null ? !service.equals(promotion.service) : promotion.service != null)
            return false;
        return description != null ? description.equals(promotion.description) : promotion.description == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (barberShopName != null ? barberShopName.hashCode() : 0);
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", barberShopName='" + barberShopName + '\'' +
                ", service='" + service + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
