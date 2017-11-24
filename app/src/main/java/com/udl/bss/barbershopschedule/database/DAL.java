package com.udl.bss.barbershopschedule.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.database.BE.BarberShopContract.BarberShopEntry;
import com.udl.bss.barbershopschedule.database.BE.ReviewContract.ReviewEntry;
import com.udl.bss.barbershopschedule.database.BE.ServiceContract.ServiceEntry;
import com.udl.bss.barbershopschedule.database.BE.AppointmentContract.AppointmentEntry;
import com.udl.bss.barbershopschedule.database.BE.PromotionContract.PromotionEntry;
import com.udl.bss.barbershopschedule.database.BE.SpecialDayContract.SpecialDayEntry;
import com.udl.bss.barbershopschedule.database.BE.ScheduleContract.ScheduleEntry;
import com.udl.bss.barbershopschedule.domain.Barber;

import java.util.ArrayList;

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

    public Barber Get_BarberShop(int barber_shop_Id) {
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

        String selection = BarberShopEntry._ID + " = " + barber_shop_Id;

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

    public long Insert_BarberShop(Barber new_barber_shop)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BarberShopEntry._ID, new_barber_shop.getId());
        values.put(BarberShopEntry.EMAIL, new_barber_shop.getEmail());
        values.put(BarberShopEntry.PHONE, new_barber_shop.getPhone());
        values.put(BarberShopEntry.NAME, new_barber_shop.getName());
        values.put(BarberShopEntry.ADDRESS, new_barber_shop.getAddress());
        values.put(BarberShopEntry.CITY, new_barber_shop.getCity());
        values.put(BarberShopEntry.DESCRIPTION, new_barber_shop.getDescription());

        return db.insert(BarberShopEntry.TABLE_NAME, null, values);
    }
}
