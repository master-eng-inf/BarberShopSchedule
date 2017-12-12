package com.udl.bss.barbershopschedule.domain;

import android.graphics.Bitmap;

/**
 * Created by Alex on 11/11/2017.
 */

public class Client {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String gender;
    private int age;
    private String password;
    private Bitmap image;
    private String imagePath;

    public Client(int id, String name, String phone, String email, String gender, int age)
    {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.age = age;
    }

    public Client(int id, String name, String email, String password, String phone, String gender,
                  int age, String imagePath)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
        this.imagePath = imagePath;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImagePath() {
        return imagePath;
    }
}
