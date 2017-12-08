package com.udl.bss.barbershopschedule.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.database.BE.BarberShopContract.BarberShopEntry;
import com.udl.bss.barbershopschedule.database.BE.ClientContract.ClientEntry;
import com.udl.bss.barbershopschedule.database.BE.ReviewContract.ReviewEntry;
import com.udl.bss.barbershopschedule.database.BE.ServiceContract.ServiceEntry;
import com.udl.bss.barbershopschedule.database.BE.AppointmentContract.AppointmentEntry;
import com.udl.bss.barbershopschedule.database.BE.PromotionContract.PromotionEntry;
import com.udl.bss.barbershopschedule.database.BE.SpecialDayContract.SpecialDayEntry;
import com.udl.bss.barbershopschedule.database.BE.ScheduleContract.ScheduleEntry;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.domain.Review;
import com.udl.bss.barbershopschedule.domain.Schedule;
import com.udl.bss.barbershopschedule.domain.SpecialDay;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Alex on 24/11/2017.
 */

public class DAL extends SQLiteOpenHelper {
    private static DAL instance = null;
    private static final String DATABASE_NAME = "barber_shop.db";
    private static final int DATABASE_VERSION = 1;
    private static Context context;

    private DAL(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DAL GetInstance(Context context) {
        if (instance == null) {
            DAL.context = context;
            instance = new DAL(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_BARBER_SHOP_TABLE = "CREATE TABLE " + BarberShopEntry.TABLE_NAME + "(" +
                BarberShopEntry._ID + " INTEGER PRIMARY KEY, " +
                BarberShopEntry.EMAIL + " TEXT, " +
                BarberShopEntry.PHONE + " TEXT, " +
                BarberShopEntry.NAME + " TEXT, " +
                BarberShopEntry.ADDRESS + " TEXT, " +
                BarberShopEntry.CITY + " TEXT, " +
                BarberShopEntry.DESCRIPTION + " TEXT);";

        String SQL_CREATE_CLIENT_TABLE = "CREATE TABLE " + ClientEntry.TABLE_NAME + "(" +
                ClientEntry._ID + " INTEGER PRIMARY KEY, " +
                ClientEntry.EMAIL + " TEXT, " +
                ClientEntry.PHONE + " TEXT, " +
                ClientEntry.NAME + " TEXT, " +
                ClientEntry.GENDER + " TEXT, " +
                ClientEntry.AGE + " INTEGER);";

        String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE " + ReviewEntry.TABLE_NAME + "(" +
                ReviewEntry.CLIENT_ID + " INTEGER, " +
                ReviewEntry.BARBER_SHOP_ID + " INTEGER, " +
                ReviewEntry.DESCRIPTION + " TEXT, " +
                ReviewEntry.MARK + " REAL, " +
                ReviewEntry.DATE + " TEXT, " +
                "PRIMARY KEY (" + ReviewEntry.CLIENT_ID + ", " + ReviewEntry.BARBER_SHOP_ID + "));";

        String SQL_CREATE_SERVICE_TABLE = "CREATE TABLE " + ServiceEntry.TABLE_NAME + "(" +
                ServiceEntry._ID + " INTEGER PRIMARY KEY, " +
                ServiceEntry.BARBER_SHOP_ID + " INTEGER, " +
                ServiceEntry.NAME + " TEXT, " +
                ServiceEntry.PRICE + " REAL, " +
                ServiceEntry.DURATION + " REAL);";

        String SQL_CREATE_APPOINTMENT_TABLE = "CREATE TABLE " + AppointmentEntry.TABLE_NAME + "(" +
                AppointmentEntry._ID + " INTEGER PRIMARY KEY, " +
                AppointmentEntry.CLIENT_ID + " INTEGER, " +
                AppointmentEntry.BARBER_SHOP_ID + " INTEGER, " +
                AppointmentEntry.SERVICE_ID + " INTEGER, " +
                AppointmentEntry.PROMOTION_ID + " INTEGER, " +
                AppointmentEntry.DATE + " TEXT);";

        String SQL_CREATE_PROMOTION_TABLE = "CREATE TABLE " + PromotionEntry.TABLE_NAME + "(" +
                PromotionEntry._ID + " INTEGER PRIMARY KEY, " +
                PromotionEntry.BARBER_SHOP_ID + " INTEGER, " +
                PromotionEntry.SERVICE_ID + " INTEGER, " +
                PromotionEntry.NAME + " TEXT, " +
                PromotionEntry.DESCRIPTION + " TEXT);";

        String SQL_CREATE_SPECIAL_DAY_TABLE = "CREATE TABLE " + SpecialDayEntry.TABLE_NAME + "(" +
                SpecialDayEntry.BARBER_SHOP_ID + " INTEGER, " +
                SpecialDayEntry.DATE + " TEXT, " +
                SpecialDayEntry.TYPE + " INTEGER, " +
                "PRIMARY KEY (" + SpecialDayEntry.BARBER_SHOP_ID + ", " + SpecialDayEntry.DATE + "));";

        String SQL_CREATE_SCHEDULE_TABLE = "CREATE TABLE " + ScheduleEntry.TABLE_NAME + "(" +
                ScheduleEntry.BARBER_SHOP_ID + " INTEGER, " +
                ScheduleEntry.DAY_OF_WEEK + " TEXT, " +
                ScheduleEntry.OPPENING_1 + " TEXT, " +
                ScheduleEntry.CLOSING_1 + " TEXT, " +
                ScheduleEntry.OPPENING_2 + " TEXT, " +
                ScheduleEntry.CLOSING_2 + " TEXT, " +
                ScheduleEntry.APPOINTMENTS_AT_SAME_TIME + " INTEGER, " +
                "PRIMARY KEY (" + ScheduleEntry.BARBER_SHOP_ID + ", " + ScheduleEntry.DAY_OF_WEEK + "));";

        db.execSQL(SQL_CREATE_BARBER_SHOP_TABLE);
        db.execSQL(SQL_CREATE_CLIENT_TABLE);
        db.execSQL(SQL_CREATE_REVIEW_TABLE);
        db.execSQL(SQL_CREATE_SERVICE_TABLE);
        db.execSQL(SQL_CREATE_APPOINTMENT_TABLE);
        db.execSQL(SQL_CREATE_PROMOTION_TABLE);
        db.execSQL(SQL_CREATE_SPECIAL_DAY_TABLE);
        db.execSQL(SQL_CREATE_SCHEDULE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Barber> Get_BarberShopList() {
        ArrayList<Barber> barber_shop_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BarberShopEntry._ID,
                BarberShopEntry.EMAIL,
                BarberShopEntry.PHONE,
                BarberShopEntry.NAME,
                BarberShopEntry.ADDRESS,
                BarberShopEntry.CITY,
                BarberShopEntry.DESCRIPTION
        };

        Cursor cursor = db.query(
                BarberShopEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(BarberShopEntry._ID);
            int emailColumnIndex = cursor.getColumnIndex(BarberShopEntry.EMAIL);
            int phoneColumnIndex = cursor.getColumnIndex(BarberShopEntry.PHONE);
            int nameColumnIndex = cursor.getColumnIndex(BarberShopEntry.NAME);
            int addressColumnIndex = cursor.getColumnIndex(BarberShopEntry.ADDRESS);
            int cityColumnIndex = cursor.getColumnIndex(BarberShopEntry.CITY);
            int descriptionColumnIndex = cursor.getColumnIndex(BarberShopEntry.DESCRIPTION);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentEmail = cursor.getString(emailColumnIndex);
                String currentPhone = cursor.getString(phoneColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentAddress = cursor.getString(addressColumnIndex);
                String currentCity = cursor.getString(cityColumnIndex);
                String currentDescription = cursor.getString(descriptionColumnIndex);

                barber_shop_list.add(new Barber(currentId, currentName, currentDescription,
                        currentCity, currentAddress, currentPhone, currentEmail, BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher)));
            }

        } finally {
            cursor.close();
        }
        return barber_shop_list;
    }

    public Barber Get_BarberShop(int barber_shop_id) {
        Barber barber_shop = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BarberShopEntry._ID,
                BarberShopEntry.EMAIL,
                BarberShopEntry.PHONE,
                BarberShopEntry.NAME,
                BarberShopEntry.ADDRESS,
                BarberShopEntry.CITY,
                BarberShopEntry.DESCRIPTION
        };

        String selection = BarberShopEntry._ID + " = " + barber_shop_id;

        Cursor cursor = db.query(
                BarberShopEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(BarberShopEntry._ID);
            int emailColumnIndex = cursor.getColumnIndex(BarberShopEntry.EMAIL);
            int phoneColumnIndex = cursor.getColumnIndex(BarberShopEntry.PHONE);
            int nameColumnIndex = cursor.getColumnIndex(BarberShopEntry.NAME);
            int addressColumnIndex = cursor.getColumnIndex(BarberShopEntry.ADDRESS);
            int cityColumnIndex = cursor.getColumnIndex(BarberShopEntry.CITY);
            int descriptionColumnIndex = cursor.getColumnIndex(BarberShopEntry.DESCRIPTION);

            cursor.moveToFirst();
            int currentId = cursor.getInt(idColumnIndex);
            String currentEmail = cursor.getString(emailColumnIndex);
            String currentPhone = cursor.getString(phoneColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            String currentAddress = cursor.getString(addressColumnIndex);
            String currentCity = cursor.getString(cityColumnIndex);
            String currentDescription = cursor.getString(descriptionColumnIndex);

            barber_shop = new Barber(currentId, currentName, currentDescription,
                    currentCity, currentAddress, currentPhone, currentEmail, BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));

        } finally {
            cursor.close();
        }
        return barber_shop;
    }

    public void Insert_BarberShop(Barber new_barber_shop) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BarberShopEntry._ID, new_barber_shop.getId());
        values.put(BarberShopEntry.EMAIL, new_barber_shop.getEmail());
        values.put(BarberShopEntry.PHONE, new_barber_shop.getPhone());
        values.put(BarberShopEntry.NAME, new_barber_shop.getName());
        values.put(BarberShopEntry.ADDRESS, new_barber_shop.getAddress());
        values.put(BarberShopEntry.CITY, new_barber_shop.getCity());
        values.put(BarberShopEntry.DESCRIPTION, new_barber_shop.getDescription());

        db.insert(BarberShopEntry.TABLE_NAME, null, values);
    }

    public void Insert_BarberShops(ArrayList<Barber> new_barber_shops) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Iterator<Barber> iterator = new_barber_shops.iterator(); iterator.hasNext(); ) {
            Barber new_barber_shop = iterator.next();

            ContentValues values = new ContentValues();
            values.put(BarberShopEntry._ID, new_barber_shop.getId());
            values.put(BarberShopEntry.EMAIL, new_barber_shop.getEmail());
            values.put(BarberShopEntry.PHONE, new_barber_shop.getPhone());
            values.put(BarberShopEntry.NAME, new_barber_shop.getName());
            values.put(BarberShopEntry.ADDRESS, new_barber_shop.getAddress());
            values.put(BarberShopEntry.CITY, new_barber_shop.getCity());
            values.put(BarberShopEntry.DESCRIPTION, new_barber_shop.getDescription());

            db.insert(BarberShopEntry.TABLE_NAME, null, values);
        }
    }

    public void Delete_AllBarberShops() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BarberShopEntry.TABLE_NAME, null, null);
    }

    public ArrayList<Client> Get_ClientList() {
        ArrayList<Client> client_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ClientEntry._ID,
                ClientEntry.EMAIL,
                ClientEntry.PHONE,
                ClientEntry.NAME,
                ClientEntry.GENDER,
                ClientEntry.AGE
        };

        Cursor cursor = db.query(
                ClientEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(ClientEntry._ID);
            int emailColumnIndex = cursor.getColumnIndex(ClientEntry.EMAIL);
            int phoneColumnIndex = cursor.getColumnIndex(ClientEntry.PHONE);
            int nameColumnIndex = cursor.getColumnIndex(ClientEntry.NAME);
            int genderColumnIndex = cursor.getColumnIndex(ClientEntry.GENDER);
            int ageColumnIndex = cursor.getColumnIndex(ClientEntry.AGE);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentEmail = cursor.getString(emailColumnIndex);
                String currentPhone = cursor.getString(phoneColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentGender = cursor.getString(genderColumnIndex);
                int currentAge = cursor.getInt(ageColumnIndex);

                client_list.add(new Client(currentId, currentName, currentPhone, currentEmail,
                        currentGender, currentAge));
            }

        } finally {
            cursor.close();
        }
        return client_list;
    }

    public Client Get_Client(int client_id) {
        Client client = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ClientEntry._ID,
                ClientEntry.EMAIL,
                ClientEntry.PHONE,
                ClientEntry.NAME,
                ClientEntry.GENDER,
                ClientEntry.AGE
        };

        String selection = ClientEntry._ID + " = " + client_id;

        Cursor cursor = db.query(
                ClientEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(ClientEntry._ID);
            int emailColumnIndex = cursor.getColumnIndex(ClientEntry.EMAIL);
            int phoneColumnIndex = cursor.getColumnIndex(ClientEntry.PHONE);
            int nameColumnIndex = cursor.getColumnIndex(ClientEntry.NAME);
            int genderColumnIndex = cursor.getColumnIndex(ClientEntry.GENDER);
            int ageColumnIndex = cursor.getColumnIndex(ClientEntry.AGE);

            cursor.moveToFirst();
            int currentId = cursor.getInt(idColumnIndex);
            String currentEmail = cursor.getString(emailColumnIndex);
            String currentPhone = cursor.getString(phoneColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            String currentGender = cursor.getString(genderColumnIndex);
            int currentAge = cursor.getInt(ageColumnIndex);

            client = new Client(currentId, currentName, currentPhone, currentEmail,
                    currentGender, currentAge);

        } finally {
            cursor.close();
        }
        return client;
    }

    public void Insert_Client(Client new_client) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ClientEntry._ID, new_client.getId());
        values.put(ClientEntry.EMAIL, new_client.getEmail());
        values.put(ClientEntry.PHONE, new_client.getPhone());
        values.put(ClientEntry.NAME, new_client.getName());
        values.put(ClientEntry.GENDER, new_client.getGender());
        values.put(ClientEntry.AGE, new_client.getAge());

        db.insert(ClientEntry.TABLE_NAME, null, values);
    }

    public void Delete_AllClients() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ClientEntry.TABLE_NAME, null, null);
    }

    public ArrayList<Review> Get_BarberShopReviews(int barber_shop_id) {
        ArrayList<Review> barber_shop_reviews = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ReviewEntry.CLIENT_ID,
                ReviewEntry.BARBER_SHOP_ID,
                ReviewEntry.DESCRIPTION,
                ReviewEntry.MARK,
                ReviewEntry.DATE,
        };

        String selection = ReviewEntry.BARBER_SHOP_ID + " = " + barber_shop_id;

        Cursor cursor = db.query(
                ReviewEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int clientIdColumnIndex = cursor.getColumnIndex(ReviewEntry.CLIENT_ID);
            int barberShopIdColumnIndex = cursor.getColumnIndex(ReviewEntry.BARBER_SHOP_ID);
            int descriptionColumnIndex = cursor.getColumnIndex(ReviewEntry.DESCRIPTION);
            int markColumnIndex = cursor.getColumnIndex(ReviewEntry.MARK);
            int dateColumnIndex = cursor.getColumnIndex(ReviewEntry.DATE);

            while (cursor.moveToNext()) {
                int currentClientId = cursor.getInt(clientIdColumnIndex);
                int currentBarberShopId = cursor.getInt(barberShopIdColumnIndex);
                String currentDescription = cursor.getString(descriptionColumnIndex);
                double currentMark = cursor.getDouble(markColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);

                barber_shop_reviews.add(new Review(currentClientId, currentBarberShopId, currentDescription,
                        currentMark, currentDate));
            }

        } finally {
            cursor.close();
        }
        return barber_shop_reviews;
    }

    public Review Get_ClientReviewForBarberShop(int client_id, int barber_shop_id) {
        Review review = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ReviewEntry.CLIENT_ID,
                ReviewEntry.BARBER_SHOP_ID,
                ReviewEntry.DESCRIPTION,
                ReviewEntry.MARK,
                ReviewEntry.DATE,
        };

        String selection = ReviewEntry.BARBER_SHOP_ID + " = " + barber_shop_id +
                " and " + ReviewEntry.CLIENT_ID + " = " + client_id;

        Cursor cursor = db.query(
                ReviewEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int clientIdColumnIndex = cursor.getColumnIndex(ReviewEntry.CLIENT_ID);
            int barberShopIdColumnIndex = cursor.getColumnIndex(ReviewEntry.BARBER_SHOP_ID);
            int descriptionColumnIndex = cursor.getColumnIndex(ReviewEntry.DESCRIPTION);
            int markColumnIndex = cursor.getColumnIndex(ReviewEntry.MARK);
            int dateColumnIndex = cursor.getColumnIndex(ReviewEntry.DATE);

            cursor.moveToFirst();
            int currentClientId = cursor.getInt(clientIdColumnIndex);
            int currentBarberShopId = cursor.getInt(barberShopIdColumnIndex);
            String currentDescription = cursor.getString(descriptionColumnIndex);
            double currentMark = cursor.getDouble(markColumnIndex);
            String currentDate = cursor.getString(dateColumnIndex);

            review = new Review(currentClientId, currentBarberShopId, currentDescription,
                    currentMark, currentDate);

        } catch (Exception ex) {
        } finally {
            cursor.close();
        }
        return review;
    }

    public void Insert_or_Update_Review(Review review) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ReviewEntry.CLIENT_ID, review.GetClientId());
        values.put(ReviewEntry.BARBER_SHOP_ID, review.GetBarberShopId());
        values.put(ReviewEntry.DESCRIPTION, review.GetDescription());
        values.put(ReviewEntry.MARK, review.GetMark());
        values.put(ReviewEntry.DATE, review.GetDate());

        try {
            db.insertOrThrow(ReviewEntry.TABLE_NAME, null, values);
        } catch (android.database.sqlite.SQLiteConstraintException ex) {
            String selection = ReviewEntry.BARBER_SHOP_ID + " = " + review.GetBarberShopId() +
                    " and " + ReviewEntry.CLIENT_ID + " = " + review.GetClientId();

            db.update(ReviewEntry.TABLE_NAME, values, selection, null);
        }
    }

    public void Insert_Reviews(ArrayList<Review> reviews) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Iterator<Review> iterator = reviews.iterator(); iterator.hasNext(); ) {
            Review current_review = iterator.next();
            ContentValues values = new ContentValues();

            values.put(ReviewEntry.CLIENT_ID, current_review.GetClientId());
            values.put(ReviewEntry.BARBER_SHOP_ID, current_review.GetBarberShopId());
            values.put(ReviewEntry.DESCRIPTION, current_review.GetDescription());
            values.put(ReviewEntry.MARK, current_review.GetMark());
            values.put(ReviewEntry.DATE, current_review.GetDate());

            db.insert(ReviewEntry.TABLE_NAME, null, values);
        }
    }

    public void Delete_Reviews() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ReviewEntry.TABLE_NAME, null, null);
    }

    public void Delete_Review(Review review) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = ReviewEntry.BARBER_SHOP_ID + " = " + review.GetBarberShopId() +
                " and " + ReviewEntry.CLIENT_ID + " = " + review.GetClientId();

        db.delete(ReviewEntry.TABLE_NAME, selection, null);
    }

    public ArrayList<BarberService> Get_BarberShopServices(int barber_shop_id) {
        ArrayList<BarberService> barber_shop_services = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ServiceEntry._ID,
                ServiceEntry.BARBER_SHOP_ID,
                ServiceEntry.NAME,
                ServiceEntry.PRICE,
                ServiceEntry.DURATION,
        };

        String selection = ServiceEntry.BARBER_SHOP_ID + " = " + barber_shop_id;

        Cursor cursor = db.query(
                ServiceEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(ServiceEntry._ID);
            int barberShopIdColumnIndex = cursor.getColumnIndex(ServiceEntry.BARBER_SHOP_ID);
            int nameColumnIndex = cursor.getColumnIndex(ServiceEntry.NAME);
            int priceColumnIndex = cursor.getColumnIndex(ServiceEntry.PRICE);
            int durationColumnIndex = cursor.getColumnIndex(ServiceEntry.DURATION);

            while (cursor.moveToNext()) {
                int currentServiceId = cursor.getInt(idColumnIndex);
                int currentBarberShopId = cursor.getInt(barberShopIdColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                double currentPrice = cursor.getDouble(priceColumnIndex);
                double currentDuration = cursor.getDouble(durationColumnIndex);

                barber_shop_services.add(new BarberService(currentServiceId, currentBarberShopId, currentName,
                        currentPrice, currentDuration));
            }

        } finally {
            cursor.close();
        }
        return barber_shop_services;
    }

    public BarberService Get_BarberShopService(int service_id) {
        BarberService service = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ServiceEntry._ID,
                ServiceEntry.BARBER_SHOP_ID,
                ServiceEntry.NAME,
                ServiceEntry.PRICE,
                ServiceEntry.DURATION
        };

        String selection = ServiceEntry._ID + " = " + service_id;

        Cursor cursor = db.query(
                ServiceEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(ServiceEntry._ID);
            int barberShopIdColumnIndex = cursor.getColumnIndex(ServiceEntry.BARBER_SHOP_ID);
            int nameColumnIndex = cursor.getColumnIndex(ServiceEntry.NAME);
            int priceColumnIndex = cursor.getColumnIndex(ServiceEntry.PRICE);
            int durationColumnIndex = cursor.getColumnIndex(ServiceEntry.DURATION);

            cursor.moveToFirst();
            int currentServiceId = cursor.getInt(idColumnIndex);
            int currentBarberShopId = cursor.getInt(barberShopIdColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            double currentPrice = cursor.getDouble(priceColumnIndex);
            double currentDuration = cursor.getDouble(durationColumnIndex);

            service = new BarberService(currentServiceId, currentBarberShopId, currentName,
                    currentPrice, currentDuration);

        } finally {
            cursor.close();
        }
        return service;
    }

    public void Insert_Services(ArrayList<BarberService> services) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Iterator<BarberService> iterator = services.iterator(); iterator.hasNext(); ) {
            BarberService current_service = iterator.next();
            ContentValues values = new ContentValues();

            values.put(ServiceEntry._ID, current_service.Get_Id());
            values.put(ServiceEntry.BARBER_SHOP_ID, current_service.Get_BarberShopId());
            values.put(ServiceEntry.NAME, current_service.Get_Name());
            values.put(ServiceEntry.PRICE, current_service.Get_Price());
            values.put(ServiceEntry.DURATION, current_service.Get_Duration());

            db.insert(ServiceEntry.TABLE_NAME, null, values);
        }
    }

    public void Delete_Services() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ServiceEntry.TABLE_NAME, null, null);
    }

    public ArrayList<Appointment> Get_AllBarberShopAppointments(int barber_shop_id) {
        ArrayList<Appointment> barber_shop_appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                AppointmentEntry._ID,
                AppointmentEntry.CLIENT_ID,
                AppointmentEntry.BARBER_SHOP_ID,
                AppointmentEntry.SERVICE_ID,
                AppointmentEntry.PROMOTION_ID,
                AppointmentEntry.DATE
        };

        String selection = AppointmentEntry.BARBER_SHOP_ID + " = " + barber_shop_id;

        Cursor cursor = db.query(
                AppointmentEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(AppointmentEntry._ID);
            int barberShopIdColumnIndex = cursor.getColumnIndex(AppointmentEntry.BARBER_SHOP_ID);
            int clientIdColumnIndex = cursor.getColumnIndex(AppointmentEntry.CLIENT_ID);
            int serviceIdColumnIndex = cursor.getColumnIndex(AppointmentEntry.SERVICE_ID);
            int promotionIdColumnIndex = cursor.getColumnIndex(AppointmentEntry.PROMOTION_ID);
            int dateColumnIndex = cursor.getColumnIndex(AppointmentEntry.DATE);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                int currentBarberShopId = cursor.getInt(barberShopIdColumnIndex);
                int currentClientId = cursor.getInt(clientIdColumnIndex);
                int currentServiceId = cursor.getInt(serviceIdColumnIndex);
                int currentPromotionId = cursor.getInt(promotionIdColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);

                barber_shop_appointments.add(new Appointment(currentId, currentClientId, currentBarberShopId, currentServiceId, currentPromotionId, currentDate));
            }

        } finally {
            cursor.close();
        }
        return barber_shop_appointments;
    }

    public ArrayList<Appointment> Get_BarberShopAppointmentsForSpecificDate(int barber_shop_id, Date date) {
        ArrayList<Appointment> barber_shop_appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                AppointmentEntry._ID,
                AppointmentEntry.CLIENT_ID,
                AppointmentEntry.BARBER_SHOP_ID,
                AppointmentEntry.SERVICE_ID,
                AppointmentEntry.PROMOTION_ID,
                AppointmentEntry.DATE
        };

        String selection = AppointmentEntry.BARBER_SHOP_ID + " = " + barber_shop_id + " and " + AppointmentEntry.DATE
                + " like " + date.getYear() + "-" + date.getMonth() + "-" + date.getDay();

        Cursor cursor = db.query(
                AppointmentEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(AppointmentEntry._ID);
            int barberShopIdColumnIndex = cursor.getColumnIndex(AppointmentEntry.BARBER_SHOP_ID);
            int clientIdColumnIndex = cursor.getColumnIndex(AppointmentEntry.CLIENT_ID);
            int serviceIdColumnIndex = cursor.getColumnIndex(AppointmentEntry.SERVICE_ID);
            int promotionIdColumnIndex = cursor.getColumnIndex(AppointmentEntry.PROMOTION_ID);
            int dateColumnIndex = cursor.getColumnIndex(AppointmentEntry.DATE);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                int currentBarberShopId = cursor.getInt(barberShopIdColumnIndex);
                int currentClientId = cursor.getInt(clientIdColumnIndex);
                int currentServiceId = cursor.getInt(serviceIdColumnIndex);
                int currentPromotionId = cursor.getInt(promotionIdColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);

                barber_shop_appointments.add(new Appointment(currentId, currentClientId, currentBarberShopId, currentServiceId, currentPromotionId, currentDate));
            }

        } finally {
            cursor.close();
        }
        return barber_shop_appointments;
    }

    public ArrayList<Appointment> Get_ClientAppointments(int client_id) {
        ArrayList<Appointment> barber_shop_appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                AppointmentEntry._ID,
                AppointmentEntry.CLIENT_ID,
                AppointmentEntry.BARBER_SHOP_ID,
                AppointmentEntry.SERVICE_ID,
                AppointmentEntry.PROMOTION_ID,
                AppointmentEntry.DATE
        };

        String selection = AppointmentEntry.CLIENT_ID + " = " + client_id;

        Cursor cursor = db.query(
                AppointmentEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(AppointmentEntry._ID);
            int barberShopIdColumnIndex = cursor.getColumnIndex(AppointmentEntry.BARBER_SHOP_ID);
            int clientIdColumnIndex = cursor.getColumnIndex(AppointmentEntry.CLIENT_ID);
            int serviceIdColumnIndex = cursor.getColumnIndex(AppointmentEntry.SERVICE_ID);
            int promotionIdColumnIndex = cursor.getColumnIndex(AppointmentEntry.PROMOTION_ID);
            int dateColumnIndex = cursor.getColumnIndex(AppointmentEntry.DATE);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                int currentBarberShopId = cursor.getInt(barberShopIdColumnIndex);
                int currentClientId = cursor.getInt(clientIdColumnIndex);
                int currentServiceId = cursor.getInt(serviceIdColumnIndex);
                int currentPromotionId = cursor.getInt(promotionIdColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);

                barber_shop_appointments.add(new Appointment(currentId, currentClientId, currentBarberShopId, currentServiceId, currentPromotionId, currentDate));
            }

        } finally {
            cursor.close();
        }
        return barber_shop_appointments;
    }

    public void Insert_Appointments(ArrayList<Appointment> appointments) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Iterator<Appointment> iterator = appointments.iterator(); iterator.hasNext(); ) {
            Appointment current_appointment = iterator.next();
            ContentValues values = new ContentValues();

            values.put(AppointmentEntry._ID, current_appointment.getId());
            values.put(AppointmentEntry.CLIENT_ID, current_appointment.getClient_id());
            values.put(AppointmentEntry.BARBER_SHOP_ID, current_appointment.getBarber_shop_id());
            values.put(AppointmentEntry.SERVICE_ID, current_appointment.getService_id());
            values.put(AppointmentEntry.PROMOTION_ID, current_appointment.getPromotion_id());
            values.put(AppointmentEntry.DATE, current_appointment.getDate());

            db.insert(AppointmentEntry.TABLE_NAME, null, values);
        }
    }

    public void Delete_Appointments() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AppointmentEntry.TABLE_NAME, null, null);
    }

    public ArrayList<Promotion> Get_BarberShopPromotions(int barber_shop_id) {
        ArrayList<Promotion> barber_shop_promotions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                PromotionEntry._ID,
                PromotionEntry.BARBER_SHOP_ID,
                PromotionEntry.SERVICE_ID,
                PromotionEntry.NAME,
                PromotionEntry.DESCRIPTION
        };

        String selection = PromotionEntry.BARBER_SHOP_ID + " = " + barber_shop_id;

        Cursor cursor = db.query(
                PromotionEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int idColumnIndex = cursor.getColumnIndex(PromotionEntry._ID);
            int barberShopIdColumnIndex = cursor.getColumnIndex(PromotionEntry.BARBER_SHOP_ID);
            int serviceIdColumnIndex = cursor.getColumnIndex(PromotionEntry.SERVICE_ID);
            int nameColumnIndex = cursor.getColumnIndex(PromotionEntry.NAME);
            int descriptionColumnIndex = cursor.getColumnIndex(PromotionEntry.DESCRIPTION);

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                int currentBarberShopId = cursor.getInt(barberShopIdColumnIndex);
                int currentServiceId = cursor.getInt(serviceIdColumnIndex);
                String currentDescription = cursor.getString(descriptionColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);

                barber_shop_promotions.add(new Promotion(currentId, currentBarberShopId, currentServiceId, currentName, currentDescription));
            }

        } finally {
            cursor.close();
        }
        return barber_shop_promotions;
    }

    public void Insert_Promotions(ArrayList<Promotion> promotions) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Iterator<Promotion> iterator = promotions.iterator(); iterator.hasNext(); ) {
            Promotion current_promotion = iterator.next();
            ContentValues values = new ContentValues();

            values.put(PromotionEntry._ID, current_promotion.getId());
            values.put(PromotionEntry.BARBER_SHOP_ID, current_promotion.getBarber_shop_id());
            values.put(PromotionEntry.SERVICE_ID, current_promotion.getService_id());
            values.put(PromotionEntry.NAME, current_promotion.getName());
            values.put(PromotionEntry.DESCRIPTION, current_promotion.getDescription());

            db.insert(PromotionEntry.TABLE_NAME, null, values);
        }
    }

    public void Delete_Promotions() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PromotionEntry.TABLE_NAME, null, null);
    }

    public ArrayList<SpecialDay> Get_BarberShopSpecialDays(int barber_shop_id) {
        ArrayList<SpecialDay> barber_shop_special_days = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                SpecialDayEntry.BARBER_SHOP_ID,
                SpecialDayEntry.DATE,
                SpecialDayEntry.TYPE
        };

        String selection = SpecialDayEntry.BARBER_SHOP_ID + " = " + barber_shop_id;

        Cursor cursor = db.query(
                SpecialDayEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int barberShopIdColumnIndex = cursor.getColumnIndex(SpecialDayEntry.BARBER_SHOP_ID);
            int dateColumnIndex = cursor.getColumnIndex(SpecialDayEntry.DATE);
            int typeColumnIndex = cursor.getColumnIndex(SpecialDayEntry.TYPE);

            while (cursor.moveToNext()) {
                int currentBarberShopId = cursor.getInt(barberShopIdColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                int currentType = cursor.getInt(typeColumnIndex);

                barber_shop_special_days.add(new SpecialDay(currentBarberShopId, currentDate, currentType));
            }

        } finally {
            cursor.close();
        }
        return barber_shop_special_days;
    }

    public void Insert_SpecialDays(ArrayList<SpecialDay> special_days) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Iterator<SpecialDay> iterator = special_days.iterator(); iterator.hasNext(); ) {
            SpecialDay current_special_day = iterator.next();
            ContentValues values = new ContentValues();

            values.put(SpecialDayEntry.BARBER_SHOP_ID, current_special_day.GetBarber_shop_id());
            values.put(SpecialDayEntry.DATE, current_special_day.GetDate());
            values.put(SpecialDayEntry.TYPE, current_special_day.Get_day_type());

            db.insert(SpecialDayEntry.TABLE_NAME, null, values);
        }
    }

    public void Delete_SpecialDays() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SpecialDayEntry.TABLE_NAME, null, null);
    }

    public ArrayList<Schedule> Get_BarberShopSchedules(int barber_shop_id) {
        ArrayList<Schedule> barber_shop_schedules = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ScheduleEntry.BARBER_SHOP_ID,
                ScheduleEntry.DAY_OF_WEEK,
                ScheduleEntry.OPPENING_1,
                ScheduleEntry.CLOSING_1,
                ScheduleEntry.OPPENING_2,
                ScheduleEntry.CLOSING_2,
                ScheduleEntry.APPOINTMENTS_AT_SAME_TIME
        };

        String selection = ScheduleEntry.BARBER_SHOP_ID + " = " + barber_shop_id;

        Cursor cursor = db.query(
                ScheduleEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int barberShopIdColumnIndex = cursor.getColumnIndex(ScheduleEntry.BARBER_SHOP_ID);
            int dayOfWeekColumnIndex = cursor.getColumnIndex(ScheduleEntry.DAY_OF_WEEK);
            int oppening1ColumnIndex = cursor.getColumnIndex(ScheduleEntry.OPPENING_1);
            int closing1ColumnIndex = cursor.getColumnIndex(ScheduleEntry.CLOSING_1);
            int oppening2ColumnIndex = cursor.getColumnIndex(ScheduleEntry.OPPENING_2);
            int closing2ColumnIndex = cursor.getColumnIndex(ScheduleEntry.CLOSING_2);
            int appointments_at_same_timeColumnIndex = cursor.getColumnIndex(ScheduleEntry.APPOINTMENTS_AT_SAME_TIME);

            while (cursor.moveToNext()) {
                int currentBarberShopId = cursor.getInt(barberShopIdColumnIndex);
                int currentDayOfWeek = cursor.getInt(dayOfWeekColumnIndex);
                String currentOpening1 = cursor.getString(oppening1ColumnIndex);
                String currentClosing1 = cursor.getString(closing1ColumnIndex);
                String currentOpening2 = cursor.getString(oppening2ColumnIndex);
                String currentClosing2 = cursor.getString(closing2ColumnIndex);
                int currentAppointment_at_same_time = cursor.getInt(appointments_at_same_timeColumnIndex);

                barber_shop_schedules.add(new Schedule(currentBarberShopId, currentDayOfWeek,
                        currentOpening1, currentClosing1, currentOpening2, currentClosing2,
                        currentAppointment_at_same_time));
            }

        } finally {
            cursor.close();
        }
        return barber_shop_schedules;
    }

    public Schedule Get_BarberShopScheduleForSpecificDay(int barber_shop_id, int day) {
        Schedule schedule = null;
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ScheduleEntry.BARBER_SHOP_ID,
                ScheduleEntry.DAY_OF_WEEK,
                ScheduleEntry.OPPENING_1,
                ScheduleEntry.CLOSING_1,
                ScheduleEntry.OPPENING_2,
                ScheduleEntry.CLOSING_2,
                ScheduleEntry.APPOINTMENTS_AT_SAME_TIME
        };

        String selection = ScheduleEntry.BARBER_SHOP_ID + " = " + barber_shop_id + " and " + ScheduleEntry.DAY_OF_WEEK + " = " + day;

        Cursor cursor = db.query(
                ScheduleEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
                null
        );

        try {
            int barberShopIdColumnIndex = cursor.getColumnIndex(ScheduleEntry.BARBER_SHOP_ID);
            int dayOfWeekColumnIndex = cursor.getColumnIndex(ScheduleEntry.DAY_OF_WEEK);
            int oppening1ColumnIndex = cursor.getColumnIndex(ScheduleEntry.OPPENING_1);
            int closing1ColumnIndex = cursor.getColumnIndex(ScheduleEntry.CLOSING_1);
            int oppening2ColumnIndex = cursor.getColumnIndex(ScheduleEntry.OPPENING_2);
            int closing2ColumnIndex = cursor.getColumnIndex(ScheduleEntry.CLOSING_2);
            int appointments_at_same_timeColumnIndex = cursor.getColumnIndex(ScheduleEntry.APPOINTMENTS_AT_SAME_TIME);

            cursor.moveToFirst();

            int currentBarberShopId = cursor.getInt(barberShopIdColumnIndex);
            int currentDayOfWeek = cursor.getInt(dayOfWeekColumnIndex);
            String currentOpening1 = cursor.getString(oppening1ColumnIndex);
            String currentClosing1 = cursor.getString(closing1ColumnIndex);
            String currentOpening2 = cursor.getString(oppening2ColumnIndex);
            String currentClosing2 = cursor.getString(closing2ColumnIndex);
            int currentAppointment_at_same_time = cursor.getInt(appointments_at_same_timeColumnIndex);

            schedule = new Schedule(currentBarberShopId, currentDayOfWeek,
                    currentOpening1, currentClosing1, currentOpening2, currentClosing2,
                    currentAppointment_at_same_time);

        } catch (Exception ex) {

        } finally {
            cursor.close();
        }
        return schedule;
    }

    public void Insert_Schelues(ArrayList<Schedule> schedules) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Iterator<Schedule> iterator = schedules.iterator(); iterator.hasNext(); ) {
            Schedule current_schedule = iterator.next();
            ContentValues values = new ContentValues();

            values.put(ScheduleEntry.BARBER_SHOP_ID, current_schedule.GetBarber_shop_id());
            values.put(ScheduleEntry.DAY_OF_WEEK, current_schedule.GetDay_of_week());
            values.put(ScheduleEntry.OPPENING_1, current_schedule.GetOppening1());
            values.put(ScheduleEntry.CLOSING_1, current_schedule.GetClosing1());
            values.put(ScheduleEntry.OPPENING_2, current_schedule.GetOppening2());
            values.put(ScheduleEntry.CLOSING_2, current_schedule.GetClosing2());
            values.put(ScheduleEntry.APPOINTMENTS_AT_SAME_TIME, current_schedule.GetAppointments_at_same_time());

            db.insert(ScheduleEntry.TABLE_NAME, null, values);
        }
    }

    public void Delete_Schedules() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ScheduleEntry.TABLE_NAME, null, null);
    }
}
