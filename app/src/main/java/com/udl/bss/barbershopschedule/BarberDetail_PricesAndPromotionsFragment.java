package com.udl.bss.barbershopschedule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udl.bss.barbershopschedule.domain.Barber;

public class BarberDetail_PricesAndPromotionsFragment extends Fragment {
    private static final String BARBER_SHOP = "barber_shop";
    private Barber barber;

    public static BarberDetail_PricesAndPromotionsFragment newInstance(Barber param1) {
        BarberDetail_PricesAndPromotionsFragment fragment = new BarberDetail_PricesAndPromotionsFragment();
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
        return inflater.inflate(R.layout.fragment_barber_detail__prices_and_promotions, container, false);
    }
}
