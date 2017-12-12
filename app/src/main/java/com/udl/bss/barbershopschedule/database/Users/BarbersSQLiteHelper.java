package com.udl.bss.barbershopschedule.database.Users;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gerard on 2/12/17.
 */

public class BarbersSQLiteHelper extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE Barbers " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT," +
            "password TEXT," +
            "mail TEXT," +
            "image TEXT," +
            "placesID TEXT," +
            "phone TEXT," +
            "gender TEXT," +
            "description TEXT)";

    public BarbersSQLiteHelper(Context context, String name,
                                 SQLiteDatabase.CursorFactory factory,
                                 int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Barbers");
        db.execSQL(sqlCreate);
    }
}
