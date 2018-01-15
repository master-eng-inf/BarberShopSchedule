package com.udl.bss.barbershopschedule.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.AppointmentAdapter;
import com.udl.bss.barbershopschedule.adapters.PromotionAdapter;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.listeners.AppointmentClick;
import com.udl.bss.barbershopschedule.listeners.PromotionClick;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String CLIENT = "client";
    private Client client;
    private RecyclerView appointmentsRecyclerView;
    private RecyclerView promotionsRecycleView;

    private OnFragmentInteractionListener mListener;
    private AdView mAdView;

    public HomeFragment() {
    }


    public static HomeFragment newInstance(Client client) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        String json = gson.toJson(client);
        args.putString(CLIENT, json);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Gson gson = new Gson();
            String json = getArguments().getString(CLIENT);
            this.client = gson.fromJson(json, Client.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && getActivity() != null) {
            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);

            getActivity().getWindow().setEnterTransition(fade);
            getActivity().getWindow().setExitTransition(fade);
        }

        if (getView() != null) {
            appointmentsRecyclerView = getView().findViewById(R.id.rv);
            promotionsRecycleView = getView().findViewById(R.id.rv2);
            mAdView = getView().findViewById(R.id.adView);
        }

        if (mAdView != null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }

        if (appointmentsRecyclerView != null) {
            appointmentsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            appointmentsRecyclerView.setLayoutManager(llm);
            appointmentsRecyclerView.setAdapter(
                    new AppointmentAdapter(new ArrayList<Appointment>(), null, null, getContext()));
            setAppointmentItems();
        }

        if (promotionsRecycleView != null) {
            promotionsRecycleView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            promotionsRecycleView.setLayoutManager(llm);
            promotionsRecycleView.setAdapter(
                    new PromotionAdapter(new ArrayList<Promotion>(), null, null));
            setPromotionsItems();
        }

        FloatingActionMenu floatingActionMenu = getActivity().findViewById(R.id.fab_menu);
        floatingActionMenu.setClosedOnTouchOutside(true);
    }

    private void setAppointmentItems() {

        APIController.getInstance().getAppointmentsByClient(client.getToken(), String.valueOf(client.getId()))
                .addOnCompleteListener(new OnCompleteListener<List<Appointment>>() {
            @Override
            public void onComplete(@NonNull Task<List<Appointment>> task) {
                List<Appointment> appointmentList = task.getResult();
                Collections.sort(appointmentList);
                AppointmentAdapter adapter = new AppointmentAdapter(
                        appointmentList,
                        new AppointmentClick(getActivity(), appointmentsRecyclerView),
                        client.getToken(), getContext());
                appointmentsRecyclerView.setAdapter(adapter);
            }
        });

    }

    private void setPromotionsItems() {

        APIController.getInstance().getPromotionalPromotions(client.getToken())
                .addOnCompleteListener(new OnCompleteListener<List<Promotion>>() {
            @Override
            public void onComplete(@NonNull Task<List<Promotion>> task) {
                List<Promotion> promotionList = task.getResult();

                PromotionAdapter adapter = new PromotionAdapter(
                        promotionList,
                        new PromotionClick(getActivity(), promotionsRecycleView),
                        client.getToken());
                promotionsRecycleView.setAdapter(adapter);
            }
        });

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
