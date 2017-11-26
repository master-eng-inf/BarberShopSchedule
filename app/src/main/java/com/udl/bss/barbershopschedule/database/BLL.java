package com.udl.bss.barbershopschedule.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.database.BE.AppointmentContract;
import com.udl.bss.barbershopschedule.database.BE.BarberShopContract;
import com.udl.bss.barbershopschedule.database.BE.PromotionContract;
import com.udl.bss.barbershopschedule.database.BE.ReviewContract;
import com.udl.bss.barbershopschedule.database.BE.ScheduleContract;
import com.udl.bss.barbershopschedule.database.BE.ServiceContract;
import com.udl.bss.barbershopschedule.database.BE.SpecialDayContract;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.domain.Review;
import com.udl.bss.barbershopschedule.domain.Schedule;
import com.udl.bss.barbershopschedule.domain.SpecialDay;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Alex on 24/11/2017.
 */

public class BLL {
    private DAL dal_instance = null;

    public BLL(Context context) {
        this.dal_instance = DAL.GetInstance(context);
    }

    public ArrayList<Barber> Get_BarberShopList() {
        return this.dal_instance.Get_BarberShopList();
    }

    public Barber Get_BarberShop(int barber_shop_id) {
        return this.dal_instance.Get_BarberShop(barber_shop_id);
    }

    public void Insert_BarberShop(Barber new_barber_shop) {
        this.dal_instance.Insert_BarberShop(new_barber_shop);
    }

    public void Delete_AllBarberShops() {
        this.dal_instance.Delete_AllBarberShops();
    }

    public ArrayList<Client> Get_ClientList() {
        return this.dal_instance.Get_ClientList();
    }

    public Client Get_Client(int client_id) {
        return this.dal_instance.Get_Client(client_id);
    }

    public void Insert_Client(Client new_client) {
        this.dal_instance.Insert_Client(new_client);
    }

    public void Delete_AllClients() {
        this.dal_instance.Delete_AllClients();
    }

    public ArrayList<Review> Get_BarberShopReviews(int barber_shop_id) {
        return this.dal_instance.Get_BarberShopReviews(barber_shop_id);
    }

    public void Insert_Reviews(ArrayList<Review> reviews) {
        this.Insert_Reviews(reviews);
    }

    public void Delete_Reviews() {
        this.dal_instance.Delete_Reviews();
    }

    public ArrayList<BarberService> Get_BarberShopServices(int barber_shop_id) {
        return this.dal_instance.Get_BarberShopServices(barber_shop_id);
    }

    public BarberService Get_BarberShopService(int service_id) {
        return this.dal_instance.Get_BarberShopService(service_id);
    }

    public void Insert_Services(ArrayList<BarberService> services) {
        this.dal_instance.Insert_Services(services);
    }

    public void Delete_Services() {
        this.dal_instance.Delete_Services();
    }

    public ArrayList<Appointment> Get_BarberShopAppointments(int barber_shop_id) {
        return this.dal_instance.Get_BarberShopAppointments(barber_shop_id);
    }

    public ArrayList<Appointment> Get_ClientAppointments(int client_id)
    {
        return this.dal_instance.Get_ClientAppointments(client_id);
    }

    public void Insert_Appointments(ArrayList<Appointment> appointments) {
        this.dal_instance.Insert_Appointments(appointments);
    }

    public void Delete_Appointments() {
        this.dal_instance.Delete_Appointments();
    }

    public ArrayList<Promotion> Get_BarberShopPromotions(int barber_shop_id) {
        return this.dal_instance.Get_BarberShopPromotions(barber_shop_id);
    }

    public void Insert_Promotions(ArrayList<Promotion> promotions) {
        this.dal_instance.Insert_Promotions(promotions);
    }

    public void Delete_Promotions() {
        this.dal_instance.Delete_Promotions();
    }

    public ArrayList<SpecialDay> Get_BarberShopSpecialDays(int barber_shop_id) {
        return this.dal_instance.Get_BarberShopSpecialDays(barber_shop_id);
    }

    public void Insert_SpecialDays(ArrayList<SpecialDay> special_days) {
        this.dal_instance.Insert_SpecialDays(special_days);
    }

    public void Delete_SpecialDays() {
        this.dal_instance.Delete_SpecialDays();
    }

    public ArrayList<Schedule> Get_BarberShopSchedules(int barber_shop_id) {
        return this.dal_instance.Get_BarberShopSchedules(barber_shop_id);
    }

    public void Insert_Schelues(ArrayList<Schedule> schedules) {
        this.dal_instance.Insert_Schelues(schedules);
    }

    public void Delete_Schedules() {
        this.dal_instance.Delete_Schedules();
    }
}
