package com.udl.bss.barbershopschedule.serverCommunication.BodyData;

/**
 * Created by gerard on 27/12/17.
 */

public class PromotionUpload {

    private String id;
    private String barber_shop_id;
    private String service_id;
    private String name;
    private String description;
    private boolean is_promotional;

    public PromotionUpload(String id, String barber_shop_id, String service_id, String name, String description, boolean is_promotional) {
        this.id = id;
        this.barber_shop_id = barber_shop_id;
        this.service_id = service_id;
        this.name = name;
        this.description = description;
        this.is_promotional = is_promotional;
    }
}
