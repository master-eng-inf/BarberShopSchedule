package com.udl.bss.barbershopschedule.database.BE;


import android.provider.BaseColumns;

/**
 * Created by Alex on 24/11/2017.
 */

public class ReviewContract {

    private ReviewContract(){}

    public static final class ReviewEntry implements BaseColumns
    {
        public final static String TABLE_NAME = "Review";

        public final static String CLIENT_ID = "Client_Id";
        public final static String BARBER_SHOP_ID = "Brber_Shop_Id";
        public final static String DESCRIPTION = "Description";
        public final static String MARK = "Mark";
        public final static String DATE = "Date";
    }
}
