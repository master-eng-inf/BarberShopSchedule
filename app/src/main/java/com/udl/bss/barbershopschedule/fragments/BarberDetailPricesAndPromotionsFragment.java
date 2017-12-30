package com.udl.bss.barbershopschedule.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.PromotionAdapter;
import com.udl.bss.barbershopschedule.adapters.ServiceAdapter;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.domain.BarberService;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.listeners.BarberServiceClick;
import com.udl.bss.barbershopschedule.listeners.PromotionClick;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import java.util.ArrayList;
import java.util.List;

public class BarberDetailPricesAndPromotionsFragment extends Fragment {

    private static final String BARBER_SHOP = "barber_shop";
    private Barber barber;
    private Client client;
    private RecyclerView promotionsRecyclerView;
    private RecyclerView servicesRecyclerView;
    private SharedPreferences mPrefs;

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

        if (getActivity() != null) {
            mPrefs = getActivity().getSharedPreferences("USER", Activity.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = mPrefs.getString("user", "");
            client = gson.fromJson(json, Client.class);
        }


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

        if (client != null && barber != null) {
            APIController.getInstance().getServicesByBarber(client.getToken(), String.valueOf(barber.getId()))
                    .addOnCompleteListener(new OnCompleteListener<List<BarberService>>() {
                        @Override
                        public void onComplete(@NonNull Task<List<BarberService>> task) {
                            List<BarberService> serviceList = task.getResult();

                            ServiceAdapter adapter = new ServiceAdapter(
                                    serviceList,
                                    new BarberServiceClick(getActivity(), servicesRecyclerView),
                                    getContext());

                            servicesRecyclerView.setAdapter(adapter);
                        }
                    });
        }
    }

    private void setPromotionsItems() {

        if (client != null && barber != null) {
            APIController.getInstance().getPromotionsByBarber(client.getToken(), String.valueOf(barber.getId()))
                    .addOnCompleteListener(new OnCompleteListener<List<Promotion>>() {
                        @Override
                        public void onComplete(@NonNull Task<List<Promotion>> task) {
                            List<Promotion> promotionList = task.getResult();

                            PromotionAdapter adapter = new PromotionAdapter(
                                    promotionList,
                                    new PromotionClick(getActivity(), promotionsRecyclerView),
                                    client.getToken());
                            promotionsRecyclerView.setAdapter(adapter);
                        }
                    });
        }
    }


}
