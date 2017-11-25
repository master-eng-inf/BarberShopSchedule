package com.udl.bss.barbershopschedule.domain;


import android.os.Parcel;
import android.os.Parcelable;

public class Promotion implements Parcelable{

    private int id;
    private int barber_id;
    private String name;
    private String description;

    public Promotion(int id, int barber_id, String name ,String description) {
        this.id = id;
        this.barber_id = barber_id;
        this.name = name;
        this.description = description;
    }

    private Promotion(Parcel in) {
        this.id = in.readInt();
        this.barber_id = in.readInt();
        this.name = in.readString();
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
        dest.writeInt(barber_id);
        dest.writeString(name);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**********Getters**********/
    public int getId() {
        return id;
    }

    public int getBarberId() {
        return barber_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    /**********Getters**********/
    public void setId(int promotion_id) {
        this.id = promotion_id;
    }

    public void setBarberId(int barber_id) {
        this.barber_id = barber_id;
    }

    public void setName(String name) {
        this.name = name;
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
        if (barber_id != promotion.barber_id) return false;
        if (name != null ? name.equals(promotion.name) : promotion.name == null) return false;
        return description != null ? description.equals(promotion.description) : promotion.description == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + barber_id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", barber_id='" + barber_id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
