package com.udl.bss.barbershopschedule.serverCommunication.BodyData;

/**
 * Created by gerard on 27/12/17.
 */

public class SpecialDayUpload {

    private String barber_shop_id;
    private String date;
    private String type;

    public SpecialDayUpload(String barber_shop_id, String date, String type) {
        this.barber_shop_id = barber_shop_id;
        this.date = date;
        this.type = type;
    }
}
