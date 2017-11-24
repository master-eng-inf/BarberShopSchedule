package com.udl.bss.barbershopschedule.database.BE;

import android.provider.BaseColumns;

/**
 * Created by Alex on 24/11/2017.
 */

public class PromotionContract {

    private PromotionContract() {}

    public static final class PromotionEntry implements BaseColumns
    {
        public final static String TABLE_NAME = "Promotion";

        public final static String _ID = BaseColumns._ID;
        public final static String BARBER_SHOP_ID = "Barber_Shop_Id";
        public final static String NAME = "Name";
        public final static String DESCRIPTION = "Description";
    }
}
