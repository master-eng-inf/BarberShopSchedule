package com.udl.bss.barbershopschedule.database.BE;

import android.provider.BaseColumns;

/**
 * Created by Alex on 24/11/2017.
 */

public class BarberShopContract {

    private BarberShopContract(){}

    public static final class BarberShopEntry implements BaseColumns
    {
        public final static String TABLE_NAME = "Barber_shop";

        public final static String _ID = BaseColumns._ID;
        public final static String EMAIL = "Email";
        public final static String PHONE = "Phone";
        public final static String NAME = "Name";
        public final static String ADDRESS = "Address";
        public final static String CITY = "City";
        public final static String DESCRIPTION = "Description";
    }
}
