package com.udl.bss.barbershopschedule.database.BE;

import android.provider.BaseColumns;

/**
 * Created by Alex on 24/11/2017.
 */

public class AppointmentContract {

    private AppointmentContract(){}

    public static final class AppointmentEntry implements BaseColumns
    {
        public final static String TABLE_NAME = "Appointment";

        public final static String _ID = BaseColumns._ID;
        public final static String CLIENT_ID = "Client_Id";
        public final static String BARBER_SHOP_ID = "Barber_Shop_Id";
        public final static String SERVICE_ID = "Service_Id";
        public final static String PROMOTION_ID = "Promotion_Id";
        public final static String DATE = "Date";
    }
}
