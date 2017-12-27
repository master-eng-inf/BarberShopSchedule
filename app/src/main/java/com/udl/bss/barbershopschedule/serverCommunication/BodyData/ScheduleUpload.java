package com.udl.bss.barbershopschedule.serverCommunication.BodyData;

/**
 * Created by gerard on 27/12/17.
 */

public class ScheduleUpload {

    private String barber_shop_id;
    private String day_of_week;
    private String opening_1;
    private String closing_1;
    private String opening_2;
    private String closing_2;
    private String appointments_at_same_time;

    public ScheduleUpload(String barber_shop_id, String day_of_week, String opening_1, String closing_1, String opening_2, String closing_2, String appointments_at_same_time) {
        this.barber_shop_id = barber_shop_id;
        this.day_of_week = day_of_week;
        this.opening_1 = opening_1;
        this.closing_1 = closing_1;
        this.opening_2 = opening_2;
        this.closing_2 = closing_2;
        this.appointments_at_same_time = appointments_at_same_time;
    }
}
