package com.udl.bss.barbershopschedule.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.AppointmentAdapter;
import com.udl.bss.barbershopschedule.adapters.BarberAppointmentsAdapter;
import com.udl.bss.barbershopschedule.adapters.PendingAppointmentAdapter;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.domain.Barber;
import com.udl.bss.barbershopschedule.listeners.BarberAppointmentClick;
import com.udl.bss.barbershopschedule.serverCommunication.APIController;

import java.util.ArrayList;
import java.util.List;


public class BarberHomeFragment extends Fragment {
    private static final String BARBER_SHOP = "barber_shop";
    private Barber barber;

    private OnFragmentInteractionListener mListener;

    private RecyclerView appointmentsRecyclerView;
    private RecyclerView pendingAppointmentsRecyclerView;

    public BarberHomeFragment() {
        // Required empty public constructor
    }

    public static BarberHomeFragment newInstance(Barber barber) {
        BarberHomeFragment fragment = new BarberHomeFragment();
        Bundle args = new Bundle();
        args.putParcelable(BARBER_SHOP, barber);
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_barber_home, container, false);
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
            pendingAppointmentsRecyclerView = getView().findViewById(R.id.rv2);
        }

        if (appointmentsRecyclerView != null) {
            appointmentsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            appointmentsRecyclerView.setLayoutManager(llm);
            appointmentsRecyclerView.setAdapter(
                    new AppointmentAdapter(new ArrayList<Appointment>(), null ,null));
            setAppointmentItems();
        }

        if (pendingAppointmentsRecyclerView != null) {
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            pendingAppointmentsRecyclerView.setLayoutManager(llm);
            pendingAppointmentsRecyclerView.setAdapter(
                    new AppointmentAdapter(new ArrayList<Appointment>(), null ,null));
            setPendingAppointmentItems();
        }

        FloatingActionMenu floatingActionMenu = getActivity().findViewById(R.id.fab_menu);
        floatingActionMenu.setClosedOnTouchOutside(true);
    }

    private void setAppointmentItems() {

        APIController.getInstance().getAppointmentsByBarber(barber.getToken(), String.valueOf(barber.getId()))
                .addOnCompleteListener(new OnCompleteListener<List<Appointment>>() {
            @Override
            public void onComplete(@NonNull Task<List<Appointment>> task) {

                BarberAppointmentsAdapter adapter = new BarberAppointmentsAdapter(
                        task.getResult(),
                        new BarberAppointmentClick(getActivity(), appointmentsRecyclerView),
                        barber.getToken());
                appointmentsRecyclerView.setAdapter(adapter);
            }
        });

    }

    private void setPendingAppointmentItems() {

        APIController.getInstance().getPendingAppointmentsByBarber(barber.getToken(), String.valueOf(barber.getId()))
                .addOnCompleteListener(new OnCompleteListener<List<Appointment>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Appointment>> task) {

                        PendingAppointmentAdapter adapter = new PendingAppointmentAdapter(
                                task.getResult(),
                                barber.getToken());
                        pendingAppointmentsRecyclerView.setAdapter(adapter);
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
        void onFragmentInteraction(Uri uri);
    }
}
