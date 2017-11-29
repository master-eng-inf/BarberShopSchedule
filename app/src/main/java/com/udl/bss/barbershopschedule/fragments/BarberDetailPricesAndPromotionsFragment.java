package com.udl.bss.barbershopschedule.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.PromotionAdapter;
import com.udl.bss.barbershopschedule.adapters.ServiceAdapter;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.listeners.BarberServiceClick;
import com.udl.bss.barbershopschedule.listeners.PromotionClick;

import java.util.ArrayList;

public class BarberDetailPricesAndPromotionsFragment extends Fragment {

    private static final String BARBER_SHOP = "barber_shop";
    private Barber barber;
    private RecyclerView promotionsRecyclerView;
    private RecyclerView servicesRecyclerView;

    public static BarberDetailPricesAndPromotionsFragment newInstance(Barber param1) {
        BarberDetailPricesAndPromotionsFragment fragment = new BarberDetailPricesAndPromotionsFragment();
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
        return inflater.inflate(R.layout.fragment_barber_detail_prices_and_promotions, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null) {
            servicesRecyclerView = getView().findViewById(R.id.rv);
            promotionsRecyclerView = getView().findViewById(R.id.rv2);
        }

        if (servicesRecyclerView != null) {
            servicesRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            servicesRecyclerView.setLayoutManager(llm);

            setServicesItems();

        }

        if (promotionsRecyclerView != null) {
            promotionsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            promotionsRecyclerView.setLayoutManager(llm);

            setPromotionsItems();
        }
    }

    private void setServicesItems() {
        BLL instance = new BLL(getContext());

        ArrayList<BarberService> services = instance.Get_BarberShopServices(barber.getId());

        ServiceAdapter adapter = new ServiceAdapter(services, new BarberServiceClick(getActivity(), servicesRecyclerView), getContext());
        servicesRecyclerView.setAdapter(adapter);
    }

    private void setPromotionsItems() {
        BLL instance = new BLL(getContext());

        ArrayList<Promotion> promotions = instance.Get_BarberShopPromotions(barber.getId());

        PromotionAdapter adapter = new PromotionAdapter(promotions, new PromotionClick(getActivity(), promotionsRecyclerView), getContext());
        promotionsRecyclerView.setAdapter(adapter);
    }
}
