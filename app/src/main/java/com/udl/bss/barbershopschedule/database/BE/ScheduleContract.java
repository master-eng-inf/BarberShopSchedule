package com.udl.bss.barbershopschedule.database.BE;

import android.provider.BaseColumns;

/**
 * Created by Alex on 24/11/2017.
 */

public class ScheduleContract {

    private ScheduleContract(){}

    public static final class ScheduleEntry implements BaseColumns
    {
        public final static String TABLE_NAME = "Schedule";

        public final static String BARBER_SHOP_ID = "Barber_Shop_Id";
        public final static String DAY_OF_WEEK = "Day_of_week";
        public final static String OPPENING_1 = "Oppening_1";
        public final static String CLOSING_1 = "Closing_1";
        public final static String OPPENING_2 = "Oppening_2";
        public final static String CLOSING_2 = "Closing_2";
        public final static String APPOINTMENTS_AT_SAME_TIME = "Appointments_at_same_time";
    }
}
