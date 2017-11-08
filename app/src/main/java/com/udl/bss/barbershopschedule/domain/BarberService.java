package com.udl.bss.barbershopschedule.domain;

/**
 * Created by Alex on 08/11/2017.
 */

public class BarberService {

    private int service_id;
    private String service_description;
    private int service_price;

    public BarberService(int service_id, String service_description, int service_price) {
        this.service_id = service_id;
        this.service_description = service_description;
        this.service_price = service_price;
    }

    public String Get_Description() {
        return this.service_description;
    }

    public int Get_Price() {
        return this.service_price;
    }

    public int Get_Id()
    {
        return this.service_id;
    }

    public void Set_Description(String new_description) {
        this.service_description = new_description;
    }

    public void Set_Price(int new_price) {
        this.service_price = new_price;
    }
}

