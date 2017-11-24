package com.udl.bss.barbershopschedule.database;

import android.content.Context;

/**
 * Created by Alex on 24/11/2017.
 */

public class BLL {
    private DAL dal_instance = null;

    public BLL(Context context) {
        this.dal_instance = DAL.GetInstance(context);
    }


}
