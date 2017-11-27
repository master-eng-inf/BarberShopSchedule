package com.udl.bss.barbershopschedule.database.BE;

import android.provider.BaseColumns;

/**
 * Created by Alex on 24/11/2017.
 */

public class SpecialDayContract {

    private SpecialDayContract(){}

    public static final class SpecialDayEntry implements BaseColumns
    {
        public final static String TABLE_NAME = "SpecialDayContract";

        public final static String BARBER_SHOP_ID = "Barber_Shop_Id";
        public final static String DATE = "Date";
        public final static String TYPE = "Type";
    }
}
