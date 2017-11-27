package com.udl.bss.barbershopschedule.database.BE;

import android.provider.BaseColumns;

/**
 * Created by Alex on 24/11/2017.
 */

public class ServiceContract {

    private  ServiceContract() {}

    public static final class ServiceEntry implements BaseColumns
    {
        public final static String TABLE_NAME = "Service";

        public final static String _ID = BaseColumns._ID;
        public final static String BARBER_SHOP_ID = "Barber_Shop_Id";
        public final static String NAME = "Name";
        public final static String PRICE = "Price";
        public final static String DURATION = "Duration";
    }
}
