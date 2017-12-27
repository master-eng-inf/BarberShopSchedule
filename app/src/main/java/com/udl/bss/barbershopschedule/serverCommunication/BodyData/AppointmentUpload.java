package com.udl.bss.barbershopschedule.serverCommunication.BodyData;

/**
 * Created by gerard on 26/12/17.
 */

public class AppointmentUpload {

    private String id;
    private String client_id;
    private String barber_shop_id;
    private String service_id;
    private String promotion_id;
    private String date;

    public AppointmentUpload(String id, String client_id, String barber_shop_id,
                             String service_id, String promotion_id, String date) {
        this.id = id;
        this.client_id = client_id;
        this.barber_shop_id = barber_shop_id;
        this.service_id = service_id;
        this.promotion_id = promotion_id;
        this.date = date;
    }
}
