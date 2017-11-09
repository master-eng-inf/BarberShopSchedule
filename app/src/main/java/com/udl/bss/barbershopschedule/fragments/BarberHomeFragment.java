package com.udl.bss.barbershopschedule.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udl.bss.barbershopschedule.R;
import com.udl.bss.barbershopschedule.adapters.AppointmentAdapter;
import com.udl.bss.barbershopschedule.domain.Appointment;
import com.udl.bss.barbershopschedule.listeners.AppointmentClick;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BarberHomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView appointmentsRecyclerView;

    public BarberHomeFragment() {
        // Required empty public constructor
    }

    public static BarberHomeFragment newInstance() {
        return new BarberHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_barber_home, container, false);
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
        }


        if (appointmentsRecyclerView != null) {
            appointmentsRecyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            appointmentsRecyclerView.setLayoutManager(llm);

            setAppointmentItems();

        }

    }

    private void setAppointmentItems() {
        List<Appointment> appointmentList = new ArrayList<>();

        Appointment appointment1 = new Appointment(1, "User 1", "Hair cut", new Date("11/20/2017 15:30"));
        Appointment appointment2 = new Appointment(2, "User 2", "Tint", new Date("11/5/2017 20:00"));

        appointmentList.add(appointment1);
        appointmentList.add(appointment2);

        AppointmentAdapter adapter = new AppointmentAdapter(appointmentList, new AppointmentClick(getActivity(), appointmentsRecyclerView));
        appointmentsRecyclerView.setAdapter(adapter);
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
