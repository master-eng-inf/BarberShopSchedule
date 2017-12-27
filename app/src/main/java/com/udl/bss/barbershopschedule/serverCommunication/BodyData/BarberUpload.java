package com.udl.bss.barbershopschedule.serverCommunication.BodyData;

/**
 * Created by gerard on 27/12/17.
 */

public class BarberUpload {

    private String id;
    private String password;
    private String email;
    private String telephone;
    private String name;
    private String address;
    private String city;


    public BarberUpload(String id, String password, String email, String telephone, String name, String address, String city) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.name = name;
        this.address = address;
        this.city = city;
    }
}
