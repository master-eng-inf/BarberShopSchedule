package com.udl.bss.barbershopschedule.domain;

/**
 * Created by Alex on 08/11/2017.
 */

public class BarberService {

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
}

