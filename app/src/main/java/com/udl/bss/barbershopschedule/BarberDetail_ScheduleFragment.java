package com.udl.bss.barbershopschedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BarberDetail_ScheduleFragment extends Fragment {

    private static final String BARBER_SHOP_ID = "id";
    private int barber_shop_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            barber_shop_id = getArguments().getInt(BARBER_SHOP_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barber_detail__schedule, container, false);
    }
}
