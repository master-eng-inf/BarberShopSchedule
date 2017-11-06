package com.udl.bss.barbershopschedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udl.bss.barbershopschedule.domain.Barber;

public class BarberDetail_ScheduleFragment extends Fragment {

    private static final String BARBER_SHOP = "barber_shop";
    private Barber barber;

    public static BarberDetail_ScheduleFragment newInstance(Barber param1) {
        BarberDetail_ScheduleFragment fragment = new BarberDetail_ScheduleFragment();
        Bundle args = new Bundle();
        args.putParcelable(BARBER_SHOP, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.barber = getArguments().getParcelable(BARBER_SHOP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barber_detail__schedule, container, false);
    }
}
