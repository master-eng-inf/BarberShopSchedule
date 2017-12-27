package com.udl.bss.barbershopschedule.serverCommunication.BodyData;

/**
 * Created by gerard on 27/12/17.
 */

public class ClientUpload {

    private String id;
    private String password;
    private String email;
    private String telephone;
    private String name;
    private String gender;
    private String age;


    public ClientUpload(String id, String password, String email, String telephone, String name, String gender, String age) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
}
