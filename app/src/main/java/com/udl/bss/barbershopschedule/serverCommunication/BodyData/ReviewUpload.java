package com.udl.bss.barbershopschedule.serverCommunication.BodyData;

/**
 * Created by gerard on 27/12/17.
 */

public class ReviewUpload {

    private String client_id;
    private String barber_shop_id;
    private String description;
    private String mark;
    private String date;

    public ReviewUpload(String client_id, String barber_shop_id, String description, String mark, String date) {
        this.client_id = client_id;
        this.barber_shop_id = barber_shop_id;
        this.description = description;
        this.mark = mark;
        this.date = date;
    }

}
