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
import com.udl.bss.barbershopschedule.adapters.PriceAdapter;
import com.udl.bss.barbershopschedule.adapters.PromotionAdapter;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.Price;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.listeners.PriceClick;
import com.udl.bss.barbershopschedule.listeners.PromotionClick;

import java.util.ArrayList;
import java.util.List;

public class BarberDetailPricesAndPromotionsFragment extends Fragment {

    private static final String BARBER_SHOP = "barber_shop";
    private Barber barber;
    private RecyclerView promotionsRecyclerView;
    private RecyclerView pricesRecyclerView;

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
            pricesRecyclerView = getView().findViewById(R.id.rv);
            promotionsRecyclerView = getView().findViewById(R.id.rv2);
        }

        if (pricesRecyclerView != null) {
            pricesRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            pricesRecyclerView.setLayoutManager(llm);

            setPricesItems();

        }

        if (promotionsRecyclerView != null) {
            promotionsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            promotionsRecyclerView.setLayoutManager(llm);

            setPromotionsItems();

        }
    }

    private void setPricesItems() {
        List<Price> pricesList = new ArrayList<>();

        Price price1 = new Price(1, barber.getName(), "Hair cut", 7);
        Price price2 = new Price(2, barber.getName(), "Tint",12);

        pricesList.add(price1);
        pricesList.add(price2);

        PriceAdapter adapter = new PriceAdapter(pricesList, new PriceClick(getActivity(), pricesRecyclerView));
        pricesRecyclerView.setAdapter(adapter);
    }

    private void setPromotionsItems() {
        List<Promotion> promotionList = new ArrayList<>();

        Promotion promotion1 = new Promotion(1, barber.getName(), "Hair cut", "50% off");
        Promotion promotion2 = new Promotion(2, barber.getName(), "Tint","2x1");
        Promotion promotion3 = new Promotion(1, barber.getName(), "Hair cut", "50% off");
        Promotion promotion4 = new Promotion(2, barber.getName(), "Tint","2x1");

        promotionList.add(promotion1);
        promotionList.add(promotion2);
        promotionList.add(promotion3);
        promotionList.add(promotion4);


        PromotionAdapter adapter = new PromotionAdapter(promotionList, new PromotionClick(getActivity(), promotionsRecyclerView));
        promotionsRecyclerView.setAdapter(adapter);
    }
}
