package com.udl.bss.barbershopschedule.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.AppointmentAdapter;
import com.udl.bss.barbershopschedule.adapters.PromotionAdapter;
import com.udl.bss.barbershopschedule.database.BLL;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.Client;
import com.udl.bss.barbershopschedule.domain.Promotion;
import com.udl.bss.barbershopschedule.listeners.AppointmentClick;
import com.udl.bss.barbershopschedule.listeners.PromotionClick;

import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String CLIENT = "client";
    private Client client;
    private RecyclerView appointmentsRecyclerView;
    private RecyclerView promotionsRecycleView;
    private BLL instance;

    private OnFragmentInteractionListener mListener;

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
        this.instance = new BLL(getContext());
        this.instance.Initialize_Database();
        //this.instance.Initialize_Clients();
        //Log.d("", "onCreate: pozvananaaa");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);

            getActivity().getWindow().setEnterTransition(fade);
            getActivity().getWindow().setExitTransition(fade);
        }

        if (getView() != null) {
            appointmentsRecyclerView = getView().findViewById(R.id.rv);
            promotionsRecycleView = getView().findViewById(R.id.rv2);
        }


        if (appointmentsRecyclerView != null) {
            appointmentsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            appointmentsRecyclerView.setLayoutManager(llm);

            setAppointmentItems();
        }

        if (promotionsRecycleView != null) {
            promotionsRecycleView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            promotionsRecycleView.setLayoutManager(llm);

            setPromotionsItems();
        }

        FloatingActionMenu floatingActionMenu = getActivity().findViewById(R.id.fab_menu);
        floatingActionMenu.setClosedOnTouchOutside(true);
    }

    private void setAppointmentItems() {
        List<Appointment> appointmentList;

        appointmentList = this.instance.Get_ClientAppointments(client.getId());

        Collections.sort(appointmentList);

        AppointmentAdapter adapter = new AppointmentAdapter(appointmentList, new AppointmentClick(getActivity(), appointmentsRecyclerView), getContext());
        appointmentsRecyclerView.setAdapter(adapter);
    }

    private void setPromotionsItems() {
        List<Promotion> promotionList;

        promotionList = this.instance.Get_PromotionalPromotions();
        PromotionAdapter adapter = new PromotionAdapter(promotionList, new PromotionClick(getActivity(), promotionsRecycleView), getContext());
        promotionsRecycleView.setAdapter(adapter);
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
