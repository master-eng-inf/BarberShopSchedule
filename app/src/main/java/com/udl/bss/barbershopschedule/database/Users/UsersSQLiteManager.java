package com.udl.bss.barbershopschedule.database.Users;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerard on 2/12/17.
 */

public class UsersSQLiteManager {

    private Context context;
    private BarbersSQLiteHelper bsh;
    private UsersSQLiteHelper ush;


    public UsersSQLiteManager(Context context){
        this.context = context;
        initialization();
    }

    private void initialization(){
        bsh = new BarbersSQLiteHelper(context, "DBBarbers", null, 1);
        ush = new UsersSQLiteHelper(context, "DBUsers", null, 1);
    }

    public List<Barber> getRegisteredBarbers() {
        List<Barber> barberList = new ArrayList<>();

        SQLiteDatabase db = bsh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Barbers;", null);
        if (c.moveToFirst()) {
            do {
                Barber b = new Barber(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(3),
                        BitmapUtils.byteArrayToBitmap(c.getBlob(4)),
                        c.getString(5),
                        c.getString(2),
                        c.getString(6),
                        c.getString(7),
                        c.getString(8));
                barberList.add(b);
            } while (c.moveToNext());
        }

        return barberList;
    }

    public List<Client> getRegisteredUsers() {
        List<Client> clientList = new ArrayList<>();

        SQLiteDatabase db = ush.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Users;", null);
        if (c.moveToFirst()) {
            do {
                Client cl = new Client(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(3),
                        c.getString(2),
                        c.getString(4),
                        c.getString(5),
                        c.getInt(6),
                        BitmapUtils.byteArrayToBitmap(c.getBlob(7)));
                clientList.add(cl);
            } while (c.moveToNext());
        }

        return clientList;
    }

    public boolean isBarberRegistered(String user, String password) {
        SQLiteDatabase db = bsh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT name, password FROM Barbers;", null);
        if (c.moveToFirst()) {
            do {
                if (c.getString(0).equals(user) &&
                        c.getString(1).equals(password))
                    return true;
            } while (c.moveToNext());
        }

        return false;
    }

    public boolean isUserRegistered(String user, String password) {
        SQLiteDatabase db = ush.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT name, password FROM Users;", null);
        if (c.moveToFirst()) {
            do {
                if (c.getString(0).equals(user) &&
                        c.getString(1).equals(password))
                    return true;
            } while (c.moveToNext());
        }

        return false;
    }


}
