package com.udl.bss.barbershopschedule.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Appointment implements Parcelable, Comparable<Appointment> {
    private int id;
    private int clientId;
    private int barberShopId;
    private int serviceId;
    private int promotionId;
    private Date date;
    private int pending;

    public Appointment(int id, int clientId, int barberShopId, int serviceId, int promotionId, String date) {
        this.id = id;
        this.clientId = clientId;
        this.barberShopId = barberShopId;
        this.serviceId = serviceId;
        this.promotionId = promotionId;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            this.date = format.parse(date);
            System.out.println(date);
        } catch (ParseException e) {
            format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                this.date = format.parse(date);
            } catch (ParseException e1) {
                this.date = null;
                e1.printStackTrace();
            }
        }
    }

    public Appointment(int id, int clientId, int barberShopId, int serviceId, int promotionId, Date date) {
        this.id = id;
        this.clientId = clientId;
        this.barberShopId = barberShopId;
        this.serviceId = serviceId;
        this.promotionId = promotionId;
        this.date = date;
    }

    private Appointment(Parcel in) {
        this.id = in.readInt();
        this.clientId = in.readInt();
        this.barberShopId = in.readInt();
        this.serviceId = in.readInt();
        this.promotionId = in.readInt();
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
        dest.writeInt(clientId);
        dest.writeInt(barberShopId);
        dest.writeInt(serviceId);
        dest.writeInt(promotionId);
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int new_client_id) {
        this.clientId = new_client_id;
    }

    public int getBarberShopId() {
        return barberShopId;
    }

    public void setBarberShopId(int new_barber_shop_id) {
        this.barberShopId = new_barber_shop_id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int new_service_id) {
        this.serviceId = new_service_id;
    }

    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int new_promotion_id) {
        this.promotionId = new_promotion_id;
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return dateFormat.format(this.date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", clientId='" + clientId + '\'' +
                ", barberShopId='" + barberShopId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", promotionId='" + promotionId + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Appointment that = (Appointment) o;

        if (id != that.id) return false;
        if (clientId != that.clientId) return false;
        if (serviceId != that.serviceId) return false;
        if (barberShopId != that.barberShopId) return false;
        if (promotionId != that.promotionId) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (clientId);
        result = 31 * result + (barberShopId);
        result = 31 * result + (serviceId);
        result = 31 * result + (promotionId);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@NonNull Appointment o) {
        Calendar internal = Calendar.getInstance();
        Calendar external = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        try {
            internal.setTime(format.parse(getDate()));
            external.setTime(format.parse(o.getDate()));
        } catch (ParseException e) {
            return 0;
        }

        if (internal.get(Calendar.YEAR) > external.get(Calendar.YEAR)) {
            return 1;
        }
        if (internal.get(Calendar.YEAR) < external.get(Calendar.YEAR)) {
            return -1;
        }
        if (internal.get(Calendar.YEAR) == external.get(Calendar.YEAR)) {

            if (internal.get(Calendar.MONTH) > external.get(Calendar.MONTH)) {
                return 1;
            }

            if (internal.get(Calendar.MONTH) < external.get(Calendar.MONTH)) {
                return -1;
            }

            if (internal.get(Calendar.MONTH) == external.get(Calendar.MONTH)) {

                if (internal.get(Calendar.DAY_OF_MONTH) > external.get(Calendar.DAY_OF_MONTH)) {
                    return 1;
                }

                if (internal.get(Calendar.DAY_OF_MONTH) < external.get(Calendar.DAY_OF_MONTH)) {
                    return -1;
                }

                if (internal.get(Calendar.DAY_OF_MONTH) == external.get(Calendar.DAY_OF_MONTH)) {

                    if (internal.get(Calendar.HOUR_OF_DAY) > external.get(Calendar.HOUR_OF_DAY)) {
                        return 1;
                    }

                    if (internal.get(Calendar.HOUR_OF_DAY) < external.get(Calendar.HOUR_OF_DAY)) {
                        return -1;
                    }

                    if (internal.get(Calendar.HOUR_OF_DAY) == external.get(Calendar.HOUR_OF_DAY)) {

                        if (internal.get(Calendar.MINUTE) > external.get(Calendar.MINUTE)) {
                            return 1;
                        }

                        if (internal.get(Calendar.MINUTE) < external.get(Calendar.MINUTE)) {
                            return -1;
                        }

                        if (internal.get(Calendar.MINUTE) == external.get(Calendar.MINUTE)) {
                            return 0;
                        }
                    }
                }
            }
        }
        return 0;
    }
}
