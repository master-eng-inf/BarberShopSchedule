package com.udl.bss.barbershopschedule.database.Users;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Client;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerard on 2/12/17.
 */

public class UsersSQLiteManager {

    private Context context;

    public UsersSQLiteManager(Context context){
        this.context = context;
    }

    public List<Barber> getRegisteredBarbers() {
        List<Barber> barberList = new ArrayList<>();

        BarbersSQLiteHelper bsh = new BarbersSQLiteHelper(context, "DBBarbers", null, 1);
        SQLiteDatabase db = bsh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Barbers;", null);
        if (c.moveToFirst()) {
            do {
                //Bitmap bitmap = c.getString(4) == null ?
                //        BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher) :
                //        BitmapUtils.loadImageFromStorage(c.getString(4), c.getString(1));
                Barber b = new Barber(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(3),
                        c.getString(5),
                        c.getString(2),
                        c.getString(6),
                        c.getString(7),
                        c.getString(8),
                        c.getString(9),
                        c.getString(4));
                barberList.add(b);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        bsh.close();

        return barberList;
    }

    public List<Client> getRegisteredUsers() {
        List<Client> clientList = new ArrayList<>();

        UsersSQLiteHelper ush = new UsersSQLiteHelper(context, "DBUsers", null, 1);
        SQLiteDatabase db = ush.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Users;", null);
        if (c.moveToFirst()) {
            do {
                //Bitmap bitmap = c.getString(7) == null ?
                //        BitmapFactory.decodeResource(context.getApplicationContext().getResources(),R.mipmap.ic_launcher) :
                //        BitmapUtils.loadImageFromStorage(c.getString(7), c.getString(1));
                Client cl = new Client(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(3),
                        c.getString(2),
                        c.getString(4),
                        c.getString(5),
                        c.getInt(6),
                        c.getString(7));
                clientList.add(cl);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        ush.close();

        return clientList;
    }

    public boolean isBarberRegistered(String user, String password) {

        BarbersSQLiteHelper bsh = new BarbersSQLiteHelper(context, "DBBarbers", null, 1);
        SQLiteDatabase db = bsh.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT name, password FROM Barbers;", null);
        if (c.moveToFirst()) {
            do {
                if (c.getString(0).equals(user) &&
                        c.getString(1).equals(password))
                    return true;
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        bsh.close();

        return false;
    }

    public boolean isUserRegistered(String user, String password) {

        UsersSQLiteHelper ush = new UsersSQLiteHelper(context, "DBUsers", null, 1);
        SQLiteDatabase db = ush.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT name, password FROM Users;", null);
        if (c.moveToFirst()) {
            do {
                if (c.getString(0).equals(user) &&
                        c.getString(1).equals(password))
                    return true;
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        ush.close();

        return false;
    }


}
