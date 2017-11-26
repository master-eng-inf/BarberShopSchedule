package com.udl.bss.barbershopschedule.database.BE;

import android.provider.BaseColumns;

/**
 * Created by Alex on 26/11/2017.
 */

public class ClientContract {

    private ClientContract() {}

    public static final class ClientEntry implements BaseColumns
    {
        public final static String TABLE_NAME = "Client";

        public final static String _ID = BaseColumns._ID;
        public final static String EMAIL = "Email";
        public final static String PHONE = "Phone";
        public final static String NAME = "Name";
        public final static String GENDER = "Gender";
        public final static String AGE = "Age";
    }
}
