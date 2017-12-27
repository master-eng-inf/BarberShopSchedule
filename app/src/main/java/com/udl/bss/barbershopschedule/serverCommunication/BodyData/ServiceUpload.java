package com.udl.bss.barbershopschedule.serverCommunication.BodyData;

/**
 * Created by gerard on 27/12/17.
 */

public class ServiceUpload {

    private String id;
    private String barber_shop_id;
    private String name;
    private String price;
    private String duration;

    public ServiceUpload(String id, String barber_shop_id, String name, String price, String duration) {
        this.id = id;
        this.barber_shop_id = barber_shop_id;
        this.name = name;
        this.price = price;
        this.duration = duration;
    }
}
